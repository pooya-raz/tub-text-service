package org.tub.tubtextservice.application.usecase.createdocx;

import org.tub.tubtextservice.application.usecase.createdocx.dto.in.EntriesDto;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;

/**
 * This class is responsible for creating Markdown text from TUB {@link TubEntry}.
 */
public class MarkdownConverter {

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

  /**
   * Creates a section based on the given {@link TitleType}. It creates both the header and the
   * body. The order is based on the order of the {@link TitleType}.
   *
   * @param entriesDto The entries to include in the section.
   * @param titleType The {@link TitleType} of the section.
   * @return the Markdown text of the section.
   */
  public String createSection(EntriesDto entriesDto, TitleType titleType) {
    final var header = createHeader(titleType);
    final var body = createBody(entriesDto, titleType);
    return header + body;
  }

  /**
   * Creates the header of the section.
   *
   * @param titleType The {@link TitleType} of the section.
   * @return The header of the section.
   */
  private String createHeader(TitleType titleType) {
    return """
                ## %s
                """.formatted(titleType.getTitleType());
  }

  /**
   * Creates the body of the section.
   *
   * @param entriesDto The entries to include in the section.
   * @param titleType The {@link TitleType} of the section.
   * @return The body of the section.
   */
  private String createBody(EntriesDto entriesDto, TitleType titleType) {
    final var body = new StringBuilder();
    for (var entry : entriesDto.entries()) {
      if (entry.titleType().equals(titleType)) {
        body.append(createEntry(entry, titleType));
      }
    }
    return body.toString();
  }

  /**
   * Creates a single entry based on the given {@link TitleType}.
   *
   * @param tubEntry The entry to create.
   * @param titleType The {@link TitleType} of the section.
   * @return The entry as Markdown text.
   */
  private String createEntry(TubEntry tubEntry, TitleType titleType) {
    return switch (titleType) {
      case MONOGRAPH -> "Monograph";
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
}
