package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.builder.TitlePrintoutsBuilder;
import org.tub.tubtextservice.model.domain.StatusOfPublication;
import org.tub.tubtextservice.model.domain.Title;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.service.tubdata.converter.TitleConverter;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.TitlePrintouts;

import static org.assertj.core.api.Assertions.assertThat;

class TitleConverterTest {
  private TitleConverter titleConverter;

  @BeforeEach
  void setUpBeforeEach() {
    titleConverter = new TitleConverter();
  }

  @Test
  void shouldConvertTitlePrintoutsToEntry() {
    TitlePrintouts titlePrintouts =
        TitlePrintoutsBuilder.builder()
            .titleTransliterated("titleTransliterated")
            .titleArabic("titleArabic")
            .category("Category:Edited title")
            .bookType("Monograph")
            .baseText("Base text")
            .build();

    final var expected =
        Title.builder()
            .titleTransliterated("titleTransliterated")
            .titleArabic("titleArabic")
            .category(StatusOfPublication.EDITED)
            .titleType(TitleType.MONOGRAPH)
            .baseText("Base text")
            .build();

    final var actual = titleConverter.convert(titlePrintouts);
    assertThat(actual).isEqualTo(expected);
  }
}
