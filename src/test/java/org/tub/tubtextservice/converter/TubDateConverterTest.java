package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.converter.TubDateConverter;

import static org.assertj.core.api.Assertions.assertThat;

class TubDateConverterTest {

  private final TubDateConverter subject = new TubDateConverter();

  @Test
  void convertShouldReturnHijriDate() {
    final var edition = EditionPrintouts.builder().yearHijri(1442).yearGregorian(2020).build();
    final var actual = subject.convert(edition);
    final var expected = new HijriDate("1442", "2020");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnShamsiDate() {
    final var edition = EditionPrintouts.builder().yearShamsi(1399).yearGregorian(2020).build();
    final var actual = subject.convert(edition);
    final var expected = new ShamsiDate("1399", "2020");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnShamsiDateWhenBothHijriAndShamsiArePresent() {
    final var edition =
        EditionPrintouts.builder().yearHijri(1442).yearShamsi(1399).yearGregorian(2020).build();
    final var actual = subject.convert(edition);
    final var expected = new ShamsiDate("1399", "2020");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnHijriDateWhenBothHijriAndShamsiArePresentAndShamsiIsNull() {
    final var edition =
        EditionPrintouts.builder().yearHijri(1442).yearShamsi(null).yearGregorian(2020).build();
    final var actual = subject.convert(edition);
    final var expected = new HijriDate("1442", "2020");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnHijriDateWithText() {
    final var edition =
        EditionPrintouts.builder()
            .yearHijri(1442)
            .yearGregorian(2020)
            .yearHijriText("1443")
            .build();
    final var actual = subject.convert(edition);
    final var expected = new HijriDate("1443", "2020");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnShamsiDateWithText() {
    final var edition =
        EditionPrintouts.builder()
            .yearShamsi(1442)
            .yearGregorian(2020)
            .yearShamsiText("1443")
            .build();
    final var actual = subject.convert(edition);
    final var expected = new ShamsiDate("1443", "2020");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnGregorianDateWithText() {
    final var edition =
        EditionPrintouts.builder()
            .yearHijri(1442)
            .yearGregorian(2020)
            .yearGregorianText("2021")
            .build();
    final var actual = subject.convert(edition);
    final var expected = new HijriDate("1442", "2021");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnAuthorWhenIntYears() {
    final var expected = new HijriDeath("687", "1970");
    final var author = AuthorPrintouts.builder().deathHijri(687).deathGregorian(1L).build();
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnAuthorWhenStringYears() {
    final var expected = new HijriDeath("687", "1288");
    final var author =
        AuthorPrintouts.builder().deathHijriText("687").deathGregorianText("1288").build();
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnAuthorWhenStringYearsWithNonNumeric() {
    final var expected = new HijriDeath("8th century", "14th century");
    final var author =
        AuthorPrintouts.builder()
            .deathHijriText("8th century")
            .deathGregorianText("14th century")
            .build();
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldRemoveTextBeforeYearHijri() {
    final var expected = new HijriDeath("687", "1288");
    final var author =
        AuthorPrintouts.builder().deathHijriText("687").deathGregorianText("1288").build();
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }
}
