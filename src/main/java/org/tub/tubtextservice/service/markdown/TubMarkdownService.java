package org.tub.tubtextservice.service.markdown;

import java.util.List;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.domain.TitleType;

/**
 * This class is responsible for creating Markdown text from TUB {@link Entry}.
 */
public class TubMarkdownService implements TextService {

  private final SectionCreator sectionCreator;

  public TubMarkdownService(SectionCreator sectionCreator) {
    this.sectionCreator = sectionCreator;
  }

    /**
     * Creates a Pandoc flavoured Markdown text.
     *
     * @param entries A list of {@link Entry} to include in the body of text.
     * @return Markdown text.
     */
  public String createText(List<Entry> entries) {

    final var body = new StringBuilder();
    for (var titleType : TitleType.values()) {
      body.append(sectionCreator.create(entries, titleType));
    }
    return body.toString();
    }
}
