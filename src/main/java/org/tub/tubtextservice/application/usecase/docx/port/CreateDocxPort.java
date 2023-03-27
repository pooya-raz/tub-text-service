package org.tub.tubtextservice.application.usecase.docx.port;

import org.tub.tubtextservice.application.usecase.docx.dto.in.CreateDocxDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;

public interface CreateDocxPort {
  CreateDocxDto createDocx(MarkdownDto markdownDto);
}
