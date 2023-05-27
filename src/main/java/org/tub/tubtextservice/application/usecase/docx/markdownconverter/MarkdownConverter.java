package org.tub.tubtextservice.application.usecase.docx.markdownconverter;

import org.springframework.stereotype.Service;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;

/** This class is responsible for creating Markdown text from TUB {@link TubEntry}. */
@Service
public class MarkdownConverter {
  /**
   * Creates a Pandoc flavoured Markdown text.
   *
   * @param entries A list of {@link TubEntry} to include in the body of text.
   * @return Markdown text.
   */
  public String convert(final EntriesDto entries) {

    final var body = new StringBuilder();
    for (var titleType : TitleType.values()) {
      body.append(SectionFormat.create(entries, titleType));
    }
    return body.toString();
  }
}
