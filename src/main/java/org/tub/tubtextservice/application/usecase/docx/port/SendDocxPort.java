package org.tub.tubtextservice.application.usecase.docx.port;

import org.tub.tubtextservice.application.usecase.docx.dto.out.SendDocxDto;

public interface SendDocxPort {
  void send(SendDocxDto sendDocxDto);
}
