package org.tub.tubtextservice.adapter.semantic.converter;

import java.time.LocalDate;
import org.tub.tubtextservice.adapter.semantic.model.tubresponse.MediaWikiDate;

public class MediaWikiDateConverter {
  private MediaWikiDateConverter() {
    throw new IllegalStateException("Utility class and cannot be instantiated");
  }

  public static String convert(MediaWikiDate source) {
    final var date = LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
    return String.valueOf(date);
  }
}
