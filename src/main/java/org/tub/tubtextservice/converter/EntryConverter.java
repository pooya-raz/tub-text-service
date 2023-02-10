package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.domain.Category;
import org.tub.tubtextservice.model.domain.Title;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;

public class EntryConverter implements Converter<TitlePrintouts, Title> {

    @Override
    public Title convert(TitlePrintouts source){
    return Title.builder()
        .titleTransliterated(source.titleTransliterated().get(0))
        .category(Category.valueOfTub(source.category().get(0).fulltext()))
        .titleArabic(source.titleArabic().get(0))
        .titleType(TitleType.valueOfTub(source.bookType().get(0)))
        .baseText(source.baseText().get(0).fulltext())
        .build();
    }
}
