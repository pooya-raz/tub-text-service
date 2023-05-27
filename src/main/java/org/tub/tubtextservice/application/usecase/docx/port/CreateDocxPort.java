package org.tub.tubtextservice.application.usecase.docx.port;

import org.springframework.stereotype.Component;
import org.tub.tubtextservice.application.usecase.docx.dto.in.CreateDocxDto;
import org.tub.tubtextservice.application.usecase.docx.dto.out.MarkdownDto;

/**
 * Port for creating docx files. Intended to be text converters such as pandoc that take in
 * markdown.
 */
@Component
public interface CreateDocxPort {
    /**
     * Create a docx file from markdown.
     *
     * @param markdownDto markdown to convert
     * @return Path to the created docx file
     */
  CreateDocxDto createDocx(MarkdownDto markdownDto, String directory);
}
