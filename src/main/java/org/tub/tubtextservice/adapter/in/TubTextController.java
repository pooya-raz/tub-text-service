package org.tub.tubtextservice.adapter.in;

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

    @GetMapping("/entry")
    public void getEntry() {
        final var user = new UserDto("test");
        docxUseCase.createDocx(user);
    }
}
