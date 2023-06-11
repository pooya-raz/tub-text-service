package org.tub.tubtextservice.adapter.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.application.usecase.docx.CreateAndSendDocx;
import org.tub.tubtextservice.application.usecase.docx.dto.in.UserDto;

@RestController
@RequestMapping()
public class TubTextController {

    private final CreateAndSendDocx createAndSendDocx;

    public TubTextController(CreateAndSendDocx createAndSendDocx) {
        this.createAndSendDocx = createAndSendDocx;
    }

    @GetMapping("/entry")
    public void getEntry() {
        final var user = new UserDto("test");
        createAndSendDocx.createDocx(user);
    }
}
