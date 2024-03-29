package org.tub.tubtextservice.application.usecase.docx;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.application.usecase.docx.dto.in.CreateDocxDto;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.application.usecase.docx.dto.in.UserDto;
import org.tub.tubtextservice.application.usecase.docx.markdownconverter.MarkdownConverter;
import org.tub.tubtextservice.application.usecase.docx.port.CreateDocxPort;
import org.tub.tubtextservice.application.usecase.docx.port.GetEntriesPort;
import org.tub.tubtextservice.domain.TubEntry;

@ExtendWith(MockitoExtension.class)
class DocxUseCaseTest {

    private DocxUseCase subject;

    @Mock
    private GetEntriesPort getEntriesPort;

    @Mock
    private CreateDocxPort createDocxPort;

    @Mock
    private MarkdownConverter markdownConverter;

    @BeforeEach
    void setUp() {
        subject = new DocxUseCase(markdownConverter, getEntriesPort, createDocxPort);
    }

    @Test
    @DisplayName("should create and send docx")
    void createDocx() {
        final var entry = new TubEntry(null, null, null, List.of(), List.of(), List.of(), null, null);
        when(getEntriesPort.getEntries()).thenReturn(new EntriesDto(List.of(entry)));
        when(markdownConverter.convert(any())).thenReturn("text");
        when(createDocxPort.createDocx(any(), any())).thenReturn(new CreateDocxDto("docx".getBytes()));
        subject.createDocx(new UserDto("test@email.com"));
    }
}
