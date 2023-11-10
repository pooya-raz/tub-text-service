package org.tub.tubtextservice.adapter.out.file;

import static org.tub.tubtextservice.adapter.out.file.FileWatcher.waitForFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.application.usecase.docx.dto.in.CreateDocxDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;
import org.tub.tubtextservice.application.usecase.docx.port.CreateDocxPort;

@Component
public class CreateDocx implements CreateDocxPort {

    private static final Logger log = Logger.getLogger(CreateDocx.class.getName());

    private static void prepareDirectory(String directoryString, Path outputFile) {
        final var directory = Path.of(directoryString);
        if (!directory.toFile().exists()) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                log.info("Error while creating directory: " + directory);
                e.printStackTrace();
            }
        }
        if (outputFile.toFile().exists()) {
            outputFile.toFile().delete();
        }
    }

    @Override
    public CreateDocxDto createDocx(MarkdownDto markdownDto, String directory) {
        final var outputFilePath = directory + "tub.docx";
        final var outputFile = Path.of(outputFilePath);
        prepareDirectory(directory, outputFile);
        final var markdownPath = saveMarkdown(markdownDto, directory);
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
            log.info("Creating docx file: " + Arrays.toString(cmd));
            Runtime.getRuntime().exec(cmd);
            waitForFile(outputFile, 0);

        } catch (IOException e) {
            log.info("Error while creating docx file");
            e.printStackTrace();
        }
        return new CreateDocxDto(markdownPath);
    }

    private String saveMarkdown(MarkdownDto markdownDto, String directory) {
        var filePath = "";
        try {
            final var file = Path.of(directory + "markdown.md");
            Files.write(file, Collections.singleton(markdownDto.body()), StandardCharsets.UTF_8);
            filePath = file.toAbsolutePath().toString();
        } catch (IOException e) {
            log.info("Error while saving markdown file");
            e.printStackTrace();
        }
        return filePath;
    }
}
