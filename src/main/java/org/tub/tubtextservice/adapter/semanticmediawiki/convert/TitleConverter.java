package org.tub.tubtextservice.adapter.semanticmediawiki.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.TitlePrintouts;
import org.tub.tubtextservice.domain.StatusOfPublication;
import org.tub.tubtextservice.domain.Title;
import org.tub.tubtextservice.domain.TitleType;

@Component
public class TitleConverter implements Converter<TitlePrintouts, Title> {

  @Override
  public Title convert(TitlePrintouts source) {
    return Title.builder()
        .titleTransliterated(source.titleTransliterated().get(0))
        .category(StatusOfPublication.valueOfTub(source.category().get(0).fulltext()))
        .titleArabic(source.titleArabic().get(0))
        .titleType(TitleType.valueOfTub(source.bookType().get(0)))
        .baseText(source.baseText().get(0).fulltext())
        .build();
  }
}
