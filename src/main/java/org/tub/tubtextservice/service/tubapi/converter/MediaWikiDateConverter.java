package org.tub.tubtextservice.service.tubapi.converter;

import org.tub.tubtextservice.service.tubapi.model.tubresponse.MediaWikiDate;

import java.time.LocalDate;

public class MediaWikiDateConverter {
  private MediaWikiDateConverter() {
    throw new IllegalStateException("Utility class and cannot be instantiated");
  }

  public static String convert(MediaWikiDate source) {
    final var date = LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
    return String.valueOf(date);
  }
}
