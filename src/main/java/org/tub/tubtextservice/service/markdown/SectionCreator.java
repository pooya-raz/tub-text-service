package org.tub.tubtextservice.service.markdown;

import java.util.List;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.domain.TitleType;

/** This class is responsible for creating the sections of the markdown file. */
public class SectionCreator {

  /**
   * Creates a section based on the given {@link TitleType}. It creates both the header and the
   * body. The order is based on the order of the {@link TitleType}.
   *
   * @param entries The entries to include in the section.
   * @param titleType The {@link TitleType} of the section.
   * @return the Markdown text of the section.
   */
  public String create(List<Entry> entries, TitleType titleType) {
    final var header = createHeader(titleType);
    final var body = createBody(entries, titleType);
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
   * @param entries The entries to include in the section.
   * @param titleType The {@link TitleType} of the section.
   * @return The body of the section.
   */
  private String createBody(List<Entry> entries, TitleType titleType) {
    final var body = new StringBuilder();
    for (var entry : entries) {
      if (entry.titleType().equals(titleType)) {
        body.append(createEntry(entry, titleType));
      }
    }
    return body.toString();
  }

  /**
   * Creates a single entry based on the given {@link TitleType}.
   *
   * @param entry The entry to create.
   * @param titleType The {@link TitleType} of the section.
   * @return The entry as Markdown text.
   */
  private String createEntry(Entry entry, TitleType titleType) {
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
