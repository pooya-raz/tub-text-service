package org.tub.tubtextservice.application.usecase.docx;

import org.springframework.stereotype.Service;
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
public class DocxUseCase {
    private final MarkdownConverter markdownConverter;
    private final GetEntriesPort getEntriesPort;
    private final CreateDocxPort createDocxPort;
    private final SendDocxPort sendDocxPort;

    public DocxUseCase(
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
        final var text = markdownConverter.convert(entries);
        final var createDocxDto = createDocxPort.createDocx(new MarkdownDto(text), "src/main/resources/output/");
        sendDocxPort.send(new SendDocxDto(createDocxDto.docx(), user.email()));
    }
}
