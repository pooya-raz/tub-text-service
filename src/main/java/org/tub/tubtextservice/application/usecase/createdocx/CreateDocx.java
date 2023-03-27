package org.tub.tubtextservice.application.usecase.createdocx;

import org.tub.tubtextservice.application.usecase.createdocx.dto.in.UserDto;
import org.tub.tubtextservice.application.usecase.createdocx.dto.out.MarkdownDto;
import org.tub.tubtextservice.application.usecase.createdocx.dto.out.SendDocxDto;
import org.tub.tubtextservice.application.usecase.createdocx.port.CreateDocxPort;
import org.tub.tubtextservice.application.usecase.createdocx.port.GetEntriesPort;
import org.tub.tubtextservice.application.usecase.createdocx.port.SendDocxPort;

public class CreateDocx {
  private final MarkdownConverter markdownConverter;
  private final GetEntriesPort getEntriesPort;
  private final CreateDocxPort createDocxPort;
  private final SendDocxPort sendDocxPort;

  public CreateDocx(
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
