package org.tub.tubtextservice.adapter.in;

import java.io.IOException;
import java.nio.file.Files;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.application.usecase.docx.DocxUseCase;
import org.tub.tubtextservice.application.usecase.docx.dto.in.UserDto;

@RestController
@RequestMapping()
public class TubTextController {

    private final DocxUseCase docxUseCase;

    public TubTextController(DocxUseCase docxUseCase) {
        this.docxUseCase = docxUseCase;
    }

    @GetMapping(value = "/entry", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public byte[] getEntry() throws IOException {
        final var user = new UserDto("test");
        final var path = docxUseCase.createDocx(user);
        return Files.readAllBytes(path);
    }
}
