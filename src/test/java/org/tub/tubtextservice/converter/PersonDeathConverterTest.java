package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.model.domain.persondate.HijriDeath;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PersonDeathConverterTest {
  PersonDeathConverter subject;
  @Mock MediaWikiDateConvertor mediaWikiDateConvertor;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new PersonDeathConverter(mediaWikiDateConvertor);
  }

  @Test
  void convertShouldReturnAuthorWhenIntYears() {
    final var expected = new HijriDeath("687", "1288");
    final var author = AuthorPrintouts.builder().deathHijri(687).deathGregorian(1L).build();
    Mockito.when(mediaWikiDateConvertor.convert(Mockito.any())).thenReturn("1288");
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
