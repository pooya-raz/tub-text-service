package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDeathConverterTest {
  private final PersonDeathConverter subject = new PersonDeathConverter();

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
    final var author = AuthorPrintouts.builder().deathHijriText("687").deathGregorianText("1288").build();
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }
  @Test
  void convertShouldReturnAuthorWhenStringYearsWithNonNumeric() {
    final var expected = new HijriDeath("8th century", "14th century");
    final var author = AuthorPrintouts.builder().deathHijriText("8th century").deathGregorianText("14th century").build();
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldRemoveTextBeforeYearHijri() {
    final var expected = new HijriDeath("687", "1288");
    final var author = AuthorPrintouts.builder().deathHijriText("687").deathGregorianText("1288").build();
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }
}
