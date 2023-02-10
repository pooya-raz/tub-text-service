package org.tub.tubtextservice.converter;

import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;

import java.time.LocalDate;

public class MediaWikiDateConverter {

  public static String convert(MediaWikiDate source) {
    final var date = LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
    return String.valueOf(date);
  }
}
