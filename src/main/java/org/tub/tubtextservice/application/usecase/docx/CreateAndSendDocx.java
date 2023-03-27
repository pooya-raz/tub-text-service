package org.tub.tubtextservice.application.usecase.docx;

import org.tub.tubtextservice.application.usecase.docx.dto.in.UserDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.SendDocxDto;
import org.tub.tubtextservice.application.usecase.docx.port.CreateDocxPort;
import org.tub.tubtextservice.application.usecase.docx.port.GetEntriesPort;
import org.tub.tubtextservice.application.usecase.docx.port.SendDocxPort;

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

  public void createDocx(UserDto user) {
    final var entries = getEntriesPort.getEntries();
    final var text = markdownConverter.convert(entries);
    final var createDocxDto = createDocxPort.createDocx(new MarkdownDto(text));
    sendDocxPort.send(new SendDocxDto(createDocxDto.docx(), user.email()));
  }
}
