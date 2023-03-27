package org.tub.tubtextservice.application.usecase.createdocx;

import org.tub.tubtextservice.application.usecase.createdocx.dto.in.UserDto;
import org.tub.tubtextservice.application.usecase.createdocx.dto.out.SendUserDto;
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
      final var docx = createDocxPort.createDocx(text);
    final var sendUser = new SendUserDto(user.email());
    sendDocxPort.send(docx, sendUser);
    }
}
