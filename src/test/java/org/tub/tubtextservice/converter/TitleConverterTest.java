package org.tub.tubtextservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.adapter.semantic.converter.TitleConverter;
import org.tub.tubtextservice.adapter.semantic.model.tubresponse.printouts.TitlePrintouts;
import org.tub.tubtextservice.builder.TitlePrintoutsBuilder;
import org.tub.tubtextservice.domain.model.tubentry.StatusOfPublication;
import org.tub.tubtextservice.domain.model.tubentry.Title;
import org.tub.tubtextservice.domain.model.tubentry.TitleType;

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
