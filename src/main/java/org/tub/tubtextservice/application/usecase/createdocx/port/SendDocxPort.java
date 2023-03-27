package org.tub.tubtextservice.application.usecase.createdocx.port;

import org.tub.tubtextservice.application.usecase.createdocx.dto.out.SendUserDto;

public interface SendDocxPort {
  void send(Object docx, SendUserDto user);
}
