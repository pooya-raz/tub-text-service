package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;

import java.time.LocalDate;

public class MediaWikiDateConvertor implements Converter<MediaWikiDate, Integer> {

  @Override
  public Integer convert(MediaWikiDate source) {
    return LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
  }
}
