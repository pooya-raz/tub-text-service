package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.model.domain.Category;
import org.tub.tubtextservice.model.domain.Title;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.service.tubapi.converter.TitleConverter;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;

import static org.assertj.core.api.Assertions.assertThat;

class TitleConverterTest {
  private final TitleConverter titleConverter = new TitleConverter();

  @Test
  void shouldConvertTitlePrintoutsToEntry() {
    TitlePrintouts titlePrintouts =
        TitlePrintouts.builder()
            .titleTransliterated("titleTransliterated")
            .titleArabic("titleArabic")
            .category(MediaWikiPageDetails.builder().fulltext("Category:Edited title").build())
            .bookType("Monograph")
            .baseText(MediaWikiPageDetails.builder().fulltext("Base text").build())
            .build();

    final var expected =
        Title.builder()
            .titleTransliterated("titleTransliterated")
            .titleArabic("titleArabic")
            .category(Category.Edited)
            .titleType(TitleType.Monograph)
            .baseText("Base text")
            .build();

    final var actual = titleConverter.convert(titlePrintouts);
    assertThat(actual).isEqualTo(expected);
  }
}
