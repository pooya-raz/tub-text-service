package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.model.Manuscript;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
        ManuscriptPrintouts.builder()
            .manuscriptOfTitle("Title")
            .yearHijri(680)
            .yearGregorian(1648)
            .manuscriptNumber("12")
            .location("Location")
            .city(MediaWikiPageDetails.builder().fulltext("City").build())
            .build();
    final var expected = new Manuscript("Location", "City", "12", new HijriDate("680", "1648"));
    when(tubDateConverter.convert(manuscriptPrintouts)).thenReturn(new HijriDate("680", "1648"));
    final var actual = subject.convert(manuscriptPrintouts);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnWithNullFields() {
    final var manuscriptPrintouts = ManuscriptPrintouts.builder().build();
    final var expected = new Manuscript(null, null, null, null);
    final var actual = subject.convert(manuscriptPrintouts);
    assertThat(actual).isEqualTo(expected);
  }
}
