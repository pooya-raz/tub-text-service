package org.tub.tubtextservice.adapter.semanticmediawiki.convert;

import java.time.LocalDate;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.MediaWikiDate;

public class MediaWikiDateConverter {
  private MediaWikiDateConverter() {
    throw new IllegalStateException("Utility class and cannot be instantiated");
  }

  public static String convert(MediaWikiDate source) {
    final var date = LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
    return String.valueOf(date);
  }
}
