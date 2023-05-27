package org.tub.tubtextservice.application.usecase.docx.markdownconverter;

import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.domain.TitleType;

class SectionFormat {
  private SectionFormat() {
    throw new UnsupportedOperationException("SectionFormat is a utility class and cannot be instantiated");
  }
  static String create(final EntriesDto entriesDto, TitleType titleType) {
    return createHeader(titleType)+
      createBody(entriesDto, titleType);
  }

  private static String createHeader(final TitleType titleType) {
    return """
              ## %s

              """.formatted(titleType.getTitleType());
  }

  private static String createBody(final EntriesDto entriesDto, TitleType titleType) {
    final var body = new StringBuilder();
    for (var entry : entriesDto.entries()) {
      if (entry.titleType().equals(titleType)) {
        body.append(TitleTypeFormat.create(entry));
      }
    }
    return body.toString();
  }
}
