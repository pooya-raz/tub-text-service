package org.tub.tubtextservice.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.builder.ManuscriptPrintoutsBuilder;
import org.tub.tubtextservice.domain.Manuscript;
import org.tub.tubtextservice.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.usecase.tub.convert.ManuscriptConverter;
import org.tub.tubtextservice.usecase.tub.convert.TubDateConverter;

@ExtendWith(MockitoExtension.class)
class ManuscriptConverterTest {

  private ManuscriptConverter subject;
  @Mock private TubDateConverter tubDateConverter;

  @BeforeEach
  void setUp() {
    subject = new ManuscriptConverter(tubDateConverter);
  }

  @Test
  void convertShouldReturnManuscript() {
    final var manuscriptPrintouts =
        ManuscriptPrintoutsBuilder.builder()
            .manuscriptOfTitle("Title")
            .yearHijri(680)
            .yearGregorian(1648)
            .manuscriptNumber("12")
            .location("Location")
            .city("City")
            .build();
    final var expected = new Manuscript("Location", "City", "12", new HijriDate("680", "1648"));
    when(tubDateConverter.convert(manuscriptPrintouts)).thenReturn(new HijriDate("680", "1648"));
    final var actual = subject.convert(manuscriptPrintouts);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnWithNullFields() {
    final var manuscriptPrintouts = ManuscriptPrintoutsBuilder.builder().build();
    final var expected = new Manuscript(null, null, null, null);
    final var actual = subject.convert(manuscriptPrintouts);
    assertThat(actual).isEqualTo(expected);
  }
}
