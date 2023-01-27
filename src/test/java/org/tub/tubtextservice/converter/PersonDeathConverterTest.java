package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.model.domain.persondate.HijriDeath;
import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;

import java.util.List;

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
    final var author =
        new AuthorPrintouts(
            List.of("Test"),
            List.of(687),
            List.of(new MediaWikiDate(1L, "")),
            List.of(),
            List.of(),
            List.of(),
            List.of());
    Mockito.when(mediaWikiDateConvertor.convert(Mockito.any())).thenReturn("1288");
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldReturnAuthorWhenStringYears() {
    final var expected = new HijriDeath("687", "1288");
    final var author =
        new AuthorPrintouts(
            List.of("Test"),
            List.of(),
            List.of(),
            List.of(),
            List.of("687"),
            List.of("d. 1288"),
            List.of());
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void convertShouldRemoveTextBeforeYearHijri() {
    final var expected = new HijriDeath("687", "1288");
    final var author =
        new AuthorPrintouts(
            List.of("Test"),
            List.of(),
            List.of(),
            List.of(),
            List.of("687"),
            List.of("1288"),
            List.of());
    final var actual = subject.convert(author);
    assertThat(actual).isEqualTo(expected);
  }
}
