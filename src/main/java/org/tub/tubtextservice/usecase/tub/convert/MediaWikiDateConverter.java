package org.tub.tubtextservice.usecase.tub.convert;

import java.time.LocalDate;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.MediaWikiDate;

public class MediaWikiDateConverter {
  private MediaWikiDateConverter() {
    throw new IllegalStateException("Utility class and cannot be instantiated");
  }

  public static String convert(MediaWikiDate source) {
    final var date = LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
    return String.valueOf(date);
  }
}
