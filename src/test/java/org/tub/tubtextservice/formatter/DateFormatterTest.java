package org.tub.tubtextservice.formatter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.model.domain.persondate.HijriDeath;
import org.tub.tubtextservice.model.domain.persondate.ShamsiDeath;

import static org.assertj.core.api.Assertions.assertThat;

class DateFormatterTest {

  @Test
  void formatShouldReturnCorrectDatesForHijri() {
    final var expected = "(d. 687/687)";
    final var actual = DateFormatter.format(new HijriDeath("687", "687"));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void formatShouldReturnCorrectDatesForShamsi() {
    final var expected = "(d. 687Sh/687)";
    final var actual = DateFormatter.format(new ShamsiDeath("687", "687"));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void formatShouldCorrectlyHandlePrependedText() {
    final var expected = "(d. c. 687Sh/687)";
    final var actual = DateFormatter.format(new ShamsiDeath("c. 687", "c. 687"));
    assertThat(actual).isEqualTo(expected);
  }
}
