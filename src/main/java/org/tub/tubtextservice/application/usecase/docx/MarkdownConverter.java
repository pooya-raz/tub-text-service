package org.tub.tubtextservice.application.usecase.docx;

import java.util.stream.Collectors;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.domain.year.persondate.PersonDeath;
import org.tub.tubtextservice.domain.year.persondate.ShamsiDeath;

/** This class is responsible for creating Markdown text from TUB {@link TubEntry}. */
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

  private static class DateFormat {
    public static String create(final PersonDeath personDeath) {
      final var template = createTemplate(personDeath);
      return template.formatted(personDeath.year(), personDeath.gregorian());
    }

    private static String createTemplate(final PersonDeath personDeath) {
      var prependedText = "d. ";
      var nonGregorian = "%s";
      final var gregorian = "%s";
      if (personDeath instanceof ShamsiDeath) {
        nonGregorian = gregorian + "Sh";
      }
      if (personDeath.year().startsWith("fl. ")) {
        prependedText = "";
      }
      return "(" + prependedText + nonGregorian + "/" + gregorian + ")";
    }
  }

  private static class SubSectionFormat{
    private static String createManuscript(final TubEntry tubEntry) {
      final var layout = "* %s, %s (#%s), dated %s/%s";
      return tubEntry.manuscripts().stream()
              .map(
                      m ->
                              layout.formatted(
                                      m.location(),
                                      m.city(),
                                      m.manuscriptNumber(),
                                      m.date().year(),
                                      m.date().gregorian()))
              .collect(Collectors.joining(" "));
    }
  }

  private static class TitleTypeFormat {
    /** Used to create a new line in markdown */
    private static final String DOUBLE_SPACE = "  ";

    public static String create(final TubEntry tubEntry, final TitleType titleType) {
      return switch (titleType) {
        case MONOGRAPH -> createMonograph(tubEntry);
        case COMMENTARY -> "Commentary";
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
                   * %s

                   **Commentary**
                   * %s
                """
          .formatted(
              tubEntry.titleTransliterated() + DOUBLE_SPACE,
              tubEntry.titleOriginal() + DOUBLE_SPACE,
              tubEntry.person().name() + DOUBLE_SPACE,
              DateFormat.create(tubEntry.person().personDeath()),
              SubSectionFormat.createManuscript(tubEntry),
              tubEntry.editions(),
              "commentary");
    }


  }

  private static class SectionFormat {
    public static String create(final EntriesDto entriesDto, TitleType titleType) {
      final var header = createHeader(titleType);
      final var body = createBody(entriesDto, titleType);
      return header + body;
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
          body.append(TitleTypeFormat.create(entry, titleType));
        }
      }
      return body.toString();
    }
  }
}
