package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;

import java.time.LocalDate;

public class MediaWikiDateConvertor implements Converter<MediaWikiDate, String> {

  @Override
  public String convert(MediaWikiDate source) {
    final var date = LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
    return String.valueOf(date);
  }
}
