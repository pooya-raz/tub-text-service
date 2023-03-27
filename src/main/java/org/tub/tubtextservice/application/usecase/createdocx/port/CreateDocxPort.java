package org.tub.tubtextservice.application.usecase.createdocx.port;

import org.tub.tubtextservice.application.usecase.createdocx.dto.in.CreateDocxDto;
import org.tub.tubtextservice.application.usecase.createdocx.dto.out.MarkdownDto;

public interface CreateDocxPort {
  CreateDocxDto createDocx(MarkdownDto markdownDto);
}
