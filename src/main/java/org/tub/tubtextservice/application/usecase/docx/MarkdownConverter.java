package org.tub.tubtextservice.application.usecase.docx;

import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.domain.year.persondate.PersonDeath;
import org.tub.tubtextservice.domain.year.persondate.ShamsiDeath;

/** This class is responsible for creating Markdown text from TUB {@link TubEntry}. */
public class MarkdownConverter {

  /** Used to create a new line in markdown */
  public static final String DOUBLE_SPACE = "  ";

  /** Indent in markdown */
  public static final String INDENT = "   ";

  /**
   * Creates a Pandoc flavoured Markdown text.
   *
   * @param entries A list of {@link TubEntry} to include in the body of text.
   * @return Markdown text.
   */
  public String convert(EntriesDto entries) {

    final var body = new StringBuilder();
    for (var titleType : TitleType.values()) {
      body.append(createSection(entries, titleType));
    }
    return body.toString();
  }

  private String createSection(EntriesDto entriesDto, TitleType titleType) {
    final var header = createHeader(titleType);
    final var body = createBody(entriesDto, titleType);
    return header + body;
  }

  private String createHeader(TitleType titleType) {
    return """
                ## %s

                """
        .formatted(titleType.getTitleType());
  }

  private String createBody(EntriesDto entriesDto, TitleType titleType) {
    final var body = new StringBuilder();
    for (var entry : entriesDto.entries()) {
      if (entry.titleType().equals(titleType)) {
        body.append(createEntry(entry, titleType));
      }
    }
    return body.toString();
  }

  public String createPersonDate(PersonDeath personDeath) {
    final var template = createPersonDateTemplate(personDeath);
    return template.formatted(personDeath.year(), personDeath.gregorian());
  }

  private String createPersonDateTemplate(PersonDeath personDeath) {
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
  
  private String createEntry(TubEntry tubEntry, TitleType titleType) {
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

  private String createMonograph(TubEntry tubEntry) {
    return """
                1. %s
                %s
                %s
                %s

                **Principle Manuscript**
                * %s

                **Editions**
                * %s

                **Commentary**
                * %s
                """
        .formatted(
            tubEntry.titleTransliterated() + DOUBLE_SPACE,
            INDENT + tubEntry.titleOriginal() + DOUBLE_SPACE,
            INDENT + tubEntry.person().name() + DOUBLE_SPACE,
            INDENT + createPersonDate(tubEntry.person().personDeath()),
            tubEntry.manuscripts(),
            tubEntry.editions(),
            "commentary");
  }

}
