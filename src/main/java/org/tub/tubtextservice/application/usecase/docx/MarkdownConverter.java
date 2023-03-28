package org.tub.tubtextservice.application.usecase.docx;

import java.util.List;
import java.util.stream.Collectors;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.domain.Commentary;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.domain.Manuscript;
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
    public static final String END_SECTION = "\n    ";

    private static String createManuscripts(final List<Manuscript> manuscripts) {
      if (manuscripts.isEmpty()){
        return "";
      }
      final var layout = "* %s, %s (#%s), dated %s/%s";
      return manuscripts.stream()
          .map(
              m ->
                  layout.formatted(
                      m.location(),
                      m.city(),
                      m.manuscriptNumber(),
                      m.date().year(),
                      m.date().gregorian()))
          .collect(Collectors.joining(END_SECTION));
    }

    private static String createEditions(final List<Edition> editions) {
      if (editions.isEmpty()){
        return "";
      }
      final var layout = "* %s, ed. %s (%s: %s, %s/%s)";
      return editions.stream()
          .map(
              e ->
                  layout.formatted(
                      e.titleTransliterated(),
                      e.editor(),
                      e.placeOfPublication(),
                      e.publisher(),
                      e.date().year(),
                      e.date().gregorian()))
          .collect(Collectors.joining(END_SECTION));
    }

    public static String createCommentaries(final List<Commentary> commentaries) {
      if (commentaries.isEmpty()){
        return "";
      }
      final var layout = "* %s, %s %s";
      return commentaries.stream()
          .map(
              e ->
                  layout.formatted(
                      e.title(), e.author().name(), DateFormat.create(e.author().personDeath())))
          .collect(Collectors.joining(END_SECTION));
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
