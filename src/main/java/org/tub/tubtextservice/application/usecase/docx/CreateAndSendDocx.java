package org.tub.tubtextservice.application.usecase.docx;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.application.usecase.docx.dto.in.UserDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.SendDocxDto;
import org.tub.tubtextservice.application.usecase.docx.markdownconverter.MarkdownConverter;
import org.tub.tubtextservice.application.usecase.docx.port.CreateDocxPort;
import org.tub.tubtextservice.application.usecase.docx.port.GetEntriesPort;
import org.tub.tubtextservice.application.usecase.docx.port.SendDocxPort;

/**
 * This class is the use case for creating and sending a docx file.
 */
@Service
public class CreateAndSendDocx {
  private final MarkdownConverter markdownConverter;
  private final GetEntriesPort getEntriesPort;
  private final CreateDocxPort createDocxPort;
  private final SendDocxPort sendDocxPort;


  public CreateAndSendDocx(
      MarkdownConverter markdownConverter,
      GetEntriesPort getEntriesPort,
      CreateDocxPort createDocxPort,
      SendDocxPort sendDocxPort) {
    this.markdownConverter = markdownConverter;
    this.getEntriesPort = getEntriesPort;
    this.createDocxPort = createDocxPort;
    this.sendDocxPort = sendDocxPort;
  }

  /**
   * This method creates a docx file from TUB data and sends it to the user's email address.
   * @param user the user to send the docx file to
   */
  public void createDocx(UserDto user) {
    final var entries = getEntriesPort.getEntries();
    saveEntriesToFile(entries);
    final var text = markdownConverter.convert(entries);
    final var createDocxDto = createDocxPort.createDocx(new MarkdownDto(text));
    sendDocxPort.send(new SendDocxDto(createDocxDto.docx(), user.email()));
  }

  private void saveEntriesToFile(EntriesDto entries) {
    final var mapper = new ObjectMapper();
    try {
      mapper.writeValue(new java.io.File("entries.json"), entries);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
