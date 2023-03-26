package org.tub.tubtextservice.domain.markdown;

import java.util.List;
import org.tub.tubtextservice.domain.model.tubentry.TubEntry;
import org.tub.tubtextservice.domain.model.tubentry.TitleType;

/**
 * This class is responsible for creating Markdown text from TUB {@link TubEntry}.
 */
public class TubMarkdownService implements TextService {

  private final SectionCreator sectionCreator;

  public TubMarkdownService(SectionCreator sectionCreator) {
    this.sectionCreator = sectionCreator;
  }

    /**
     * Creates a Pandoc flavoured Markdown text.
     *
     * @param entries A list of {@link TubEntry} to include in the body of text.
     * @return Markdown text.
     */
  public String createText(List<TubEntry> entries) {

    final var body = new StringBuilder();
    for (var titleType : TitleType.values()) {
      body.append(sectionCreator.create(entries, titleType));
    }
    return body.toString();
    }
}
