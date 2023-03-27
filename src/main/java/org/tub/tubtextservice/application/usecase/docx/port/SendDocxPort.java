package org.tub.tubtextservice.application.usecase.docx.port;

import org.tub.tubtextservice.application.usecase.docx.dto.out.SendDocxDto;

/**
 * Interface for the port to send the docx file.
 */
public interface SendDocxPort {

    /**
     * Sends the docx file to the user.
     * @param sendDocxDto dto to be sent
     */
  void send(SendDocxDto sendDocxDto);
}
