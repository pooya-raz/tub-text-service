package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;

import static org.assertj.core.api.Assertions.assertThat;

class EditionDateConvertorTest {

  private final EditionDateConvertor subject = new EditionDateConvertor();
    @Test
    void convertShouldReturnHijriDate() {
      final var edition = EditionPrintouts.builder().yearHijri(1442).yearGregorian(2020).build();
      final var actual = subject.convert(edition);
      final var expected= new HijriDate("1442", "2020");
      assertThat(actual).isEqualTo(expected);
    }
    @Test
    void convertShouldReturnShamsiDate() {
      final var edition = EditionPrintouts.builder().yearShamsi(1399).yearGregorian(2020).build();
      final var actual = subject.convert(edition);
      final var expected= new ShamsiDate("1399", "2020");
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    void convertShouldReturnShamsiDateWhenBothHijriAndShamsiArePresent() {
      final var edition = EditionPrintouts.builder().yearHijri(1442).yearShamsi(1399).yearGregorian(2020).build();
      final var actual = subject.convert(edition);
      final var expected= new ShamsiDate("1399", "2020");
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    void convertShouldReturnHijriDateWhenBothHijriAndShamsiArePresentAndShamsiIsNull() {
      final var edition = EditionPrintouts.builder().yearHijri(1442).yearShamsi(null).yearGregorian(2020).build();
      final var actual = subject.convert(edition);
      final var expected= new HijriDate("1442", "2020");
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    void convertShouldReturnHijriDateWithText(){
      final var edition = EditionPrintouts.builder().yearHijri(1442).yearGregorian(2020).yearHijriText("1443").build();
      final var actual = subject.convert(edition);
      final var expected= new HijriDate("1443", "2020");
      assertThat(actual).isEqualTo(expected);
    }

  @Test
  void convertShouldReturnShamsiDateWithText(){
    final var edition = EditionPrintouts.builder().yearShamsi(1442).yearGregorian(2020).yearShamsiText("1443").build();
    final var actual = subject.convert(edition);
    final var expected= new ShamsiDate("1443", "2020");
    assertThat(actual).isEqualTo(expected);
  }
  @Test
  void convertShouldReturnGregorianDateWithText(){
    final var edition = EditionPrintouts.builder().yearHijri(1442).yearGregorian(2020).yearGregorianText("2021").build();
    final var actual = subject.convert(edition);
    final var expected= new HijriDate("1442", "2021");
    assertThat(actual).isEqualTo(expected);
  }
}