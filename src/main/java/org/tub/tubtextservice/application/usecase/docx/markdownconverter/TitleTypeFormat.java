package org.tub.tubtextservice.application.usecase.docx.markdownconverter;

import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;

class TitleTypeFormat {

  /** Used to create a new line in markdown */
  private static final String DOUBLE_SPACE = "  ";

  private TitleTypeFormat() {
    throw new UnsupportedOperationException(
        "TitleTypeFormat is a utility class and cannot be instantiated");
  }

  static String create(final TubEntry tubEntry, final TitleType titleType) {
    return switch (titleType) {
      case MONOGRAPH -> createMonograph(tubEntry);
      case COMMENTARY -> createCommentaries(tubEntry);
      case GLOSS -> "Gloss";
      case MARGINNOTES -> "Marginnotes";
      case TREATISE -> "Treatise";
      case SUMMARY -> "Summary";
      case POEM -> "Poem";
      case REFUTATION -> "Refutation";
      case TAQRIRAT -> "Taqrirat";
      case TRANSLATION -> "Translation";
      case UNKNOWN -> "Unknown";
    };
  }

  private static String createMonograph(final TubEntry tubEntry) {
    return """
                1. %s
                   %s
                   %s
                   %s

                   **Principle Manuscripts**
                    %s

                   **Editions**
                    %s

                   **Commentaries**
                    %s

                """
        .formatted(
            tubEntry.titleTransliterated() + DOUBLE_SPACE,
            tubEntry.titleOriginal() + DOUBLE_SPACE,
            tubEntry.person().name() + DOUBLE_SPACE,
            DateFormat.create(tubEntry.person().personDeath()),
            SubSectionFormat.createManuscripts(tubEntry.manuscripts()),
            SubSectionFormat.createEditions(tubEntry.editions()),
            SubSectionFormat.createCommentaries(tubEntry.commentaries()));
  }

  private static String createCommentaries(final TubEntry tubEntry) {
    return """
                1. %s
                   %s
                   %s
                   %s

                   **Principle Manuscripts**
                    %s

                   **Editions**
                    %s

                """
            .formatted(
                    tubEntry.titleTransliterated() + DOUBLE_SPACE,
                    tubEntry.titleOriginal() + DOUBLE_SPACE,
                    tubEntry.person().name() + DOUBLE_SPACE,
                    DateFormat.create(tubEntry.person().personDeath()),
                    SubSectionFormat.createManuscripts(tubEntry.manuscripts()),
                    SubSectionFormat.createEditions(tubEntry.editions()));
  }
}
