package org.tub.tubtextservice.application.usecase.createdocx.port;

import org.tub.tubtextservice.application.usecase.createdocx.dto.out.SendDocxDto;

public interface SendDocxPort {
  void send(SendDocxDto sendDocxDto);
}
