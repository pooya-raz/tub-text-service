package org.tub.tubtextservice.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.builder.EditionPrintoutsBuilder;
import org.tub.tubtextservice.builder.MediaWikiPageDetailsBuilder;
import org.tub.tubtextservice.domain.model.tubentry.Edition;
import org.tub.tubtextservice.domain.model.tubentry.year.editiondate.HijriDate;
import org.tub.tubtextservice.domain.tubdata.converter.EditionConverter;
import org.tub.tubtextservice.domain.tubdata.converter.TubDateConverter;

@ExtendWith(MockitoExtension.class)
class EditionConverterTest {

  private EditionConverter subject;

  @Mock private TubDateConverter editionDateConverter;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new EditionConverter(editionDateConverter);
  }

  @Test
  void convertShouldReturnEditionWithHijriDates() {
    final var editionPrintouts =
        EditionPrintoutsBuilder.builder()
            .titleTransliterated("editionName")
            .titleArabic("editionPlace")
            .publisher("editionPublisher")
            .city(MediaWikiPageDetailsBuilder.builder().fulltext("City").build())
            .description("Description")
            .yearHijri(678)
            .yearGregorian(1400)
            .editionType("Modern Print")
            .editors("Editors")
            .build();
    final var expected =
        Edition.builder()
            .titleTransliterated("editionName")
            .titleArabic("editionPlace")
            .publisher("editionPublisher")
            .placeOfPublication("City")
            .description("Description")
            .editionType("Modern Print")
            .editor("Editors")
            .date(new HijriDate("678", "1400"))
            .build();

    when(editionDateConverter.convert(editionPrintouts)).thenReturn(new HijriDate("678", "1400"));
    final var actual = subject.convert(editionPrintouts);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnEditionWithNullFieldsWhenGivenAllNullFields() {
    final var editionPrintouts = EditionPrintoutsBuilder.builder().build();
    final var expected = Edition.builder().build();
    final var actual = subject.convert(editionPrintouts);
    assertThat(actual).isEqualTo(expected);
  }
}
