package org.tub.tubtextservice.application.usecase.docx;

import java.nio.file.Path;
import org.springframework.stereotype.Service;
import org.tub.tubtextservice.application.usecase.docx.dto.in.UserDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;
import org.tub.tubtextservice.application.usecase.docx.markdownconverter.MarkdownConverter;
import org.tub.tubtextservice.application.usecase.docx.port.CreateDocxPort;
import org.tub.tubtextservice.application.usecase.docx.port.GetEntriesPort;

/**
 * This class is the use case for creating and sending a docx file.
 */
@Service
public class DocxUseCase {
    public static final String DIRECTORY = "output/";
    public static final String FILENAME = "tub.docx";
    private final MarkdownConverter markdownConverter;
    private final GetEntriesPort getEntriesPort;
    private final CreateDocxPort createDocxPort;

    public DocxUseCase(
            MarkdownConverter markdownConverter, GetEntriesPort getEntriesPort, CreateDocxPort createDocxPort) {
        this.markdownConverter = markdownConverter;
        this.getEntriesPort = getEntriesPort;
        this.createDocxPort = createDocxPort;
    }

    /**
     * This method creates a docx file from TUB data and sends it to the user's email address.
     * @param user the user to send the docx file to
     */
    public Path createDocx(UserDto user) {
        final var entries = getEntriesPort.getEntries();
        final var text = markdownConverter.convert(entries);
        final var createDocxDto = createDocxPort.createDocx(new MarkdownDto(text), DIRECTORY);
        return Path.of(DIRECTORY + FILENAME);
    }
}
