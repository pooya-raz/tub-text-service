package org.tub.tubtextservice.application.usecase.docx.markdownconverter;

import org.tub.tubtextservice.domain.TubEntry;

class TitleTypeFormat {

  public static final String LAYOUT =
      """
          1. %s
             %s
             %s
             %s
          """;
  /** Used to create a new line in markdown */
  private static final String DOUBLE_SPACE = "  ";

  private TitleTypeFormat() {
    throw new UnsupportedOperationException(
        "TitleTypeFormat is a utility class and cannot be instantiated");
  }

  static String create(final TubEntry tubEntry) {
    return LAYOUT.formatted(
            tubEntry.titleTransliterated() + DOUBLE_SPACE,
            tubEntry.titleOriginal() + DOUBLE_SPACE,
            tubEntry.person().name() + DOUBLE_SPACE,
            DateFormat.create(tubEntry.person().personDeath()))
        + SubSectionFormat.createManuscripts(tubEntry.manuscripts())
        + SubSectionFormat.createEditions(tubEntry.editions())
        + SubSectionFormat.createCommentaries(tubEntry.commentaries())
        + "\n";
  }
}
