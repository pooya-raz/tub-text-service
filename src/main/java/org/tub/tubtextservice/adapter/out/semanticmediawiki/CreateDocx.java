package org.tub.tubtextservice.adapter.out.semanticmediawiki;

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
  public CreateDocxDto createDocx(MarkdownDto markdownDto) {
    final var path = saveMarkdown(markdownDto);
    final var system = System.getProperty("os.name").toLowerCase();
    log.info("Operating System: " + system);
    if (system.contains("windows")) {
      return null;
    }
    try {
      String[] cmd = {"pandoc -f markdown -t docx -o test.docx", path};
      Runtime.getRuntime().exec(cmd);
    }catch (IOException e) {
      log.info("Error while creating docx file");
      e.printStackTrace();
    }
    return new CreateDocxDto(path);
  }

  private String saveMarkdown(MarkdownDto markdownDto) {
    var path = "";
    try {
      final var file = new File("markdown.md");
      final var fileWriter = new FileWriter(file);
      final var printWriter = new PrintWriter(fileWriter);
      printWriter.print(markdownDto.body());
      printWriter.close();
      path = file.getAbsolutePath();
    } catch (IOException e) {
      log.info("Error while saving markdown file");
      e.printStackTrace();
    }
    return path;
    }
}
