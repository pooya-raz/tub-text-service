package org.tub.tubtextservice.usecase.tub.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.EditionPrintouts;

@Component
public class EditionConverter implements Converter<EditionPrintouts, Edition> {

  private final TubDateConverter tubDateConverter;

  public EditionConverter(TubDateConverter tubDateConverter) {
    this.tubDateConverter = tubDateConverter;
  }

  @Override
  public Edition convert(EditionPrintouts editionPrintouts) {
    return Edition.builder()
        .titleTransliterated(
            editionPrintouts.titleTransliterated().stream().findFirst().orElse(null))
        .titleArabic(editionPrintouts.titleArabic().stream().findFirst().orElse(null))
        .editor(editionPrintouts.editors().stream().findFirst().orElse(null))
        .publisher(editionPrintouts.publisher().stream().findFirst().orElse(null))
        .description(editionPrintouts.description().stream().findFirst().orElse(null))
        .editionType(editionPrintouts.editionType().stream().findFirst().orElse(null))
        .placeOfPublication(
            editionPrintouts.city().stream()
                .findFirst()
                .map(MediaWikiPageDetails::fulltext)
                .orElse(null))
        .date(tubDateConverter.convert(editionPrintouts))
        .build();
  }
}
