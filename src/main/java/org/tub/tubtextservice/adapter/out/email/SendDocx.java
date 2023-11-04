package org.tub.tubtextservice.adapter.out.email;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.application.usecase.docx.dto.out.SendDocxDto;
import org.tub.tubtextservice.application.usecase.docx.port.SendDocxPort;

@Component
public class SendDocx implements SendDocxPort {

    Logger log = Logger.getLogger(SendDocx.class.getName());

    @Override
    public void send(SendDocxDto sendDocxDto) {
        log.info("Sending docx file");
    }
}
