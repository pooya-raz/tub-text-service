package org.tub.tubtextservice.adapter.out.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.application.usecase.docx.dto.in.CreateDocxDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;
import org.tub.tubtextservice.application.usecase.docx.port.CreateDocxPort;

@Component
public class CreateDocx implements CreateDocxPort {

    private static final Logger log = Logger.getLogger(CreateDocx.class.getName());

    @Override
    public CreateDocxDto createDocx(MarkdownDto markdownDto, String directory) {
        final var markdownPath = saveMarkdown(markdownDto, directory);
        final var system = System.getProperty("os.name").toLowerCase();
        final var outputFilePath = directory + "tub.docx";
        if (system.contains("windows")) {
            return null;
        }
        try {
            String[] cmd = {
                "pandoc",
                "-o",
                outputFilePath,
                "--reference-doc",
                "src/main/resources/template.docx",
                "--table-of-contents",
                markdownPath
            };
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            log.info("Error while creating docx file");
            e.printStackTrace();
        }
        return new CreateDocxDto(markdownPath);
    }

    private String saveMarkdown(MarkdownDto markdownDto, String directory) {
        var filePath = "";
        try {
            final var file = new File(directory + "markdown.md");
            final var fileWriter = new FileWriter(file);
            final var printWriter = new PrintWriter(fileWriter);
            printWriter.print(markdownDto.body());
            printWriter.close();
            filePath = file.getAbsolutePath();
        } catch (IOException e) {
            log.info("Error while saving markdown file");
            e.printStackTrace();
        }
        return filePath;
    }
}
