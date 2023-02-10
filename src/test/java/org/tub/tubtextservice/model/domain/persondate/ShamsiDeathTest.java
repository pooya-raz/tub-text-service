package org.tub.tubtextservice.model.domain.persondate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShamsiDeathTest {

  public static final String YEAR_687 = "687";
  public static final String GENERIC_TEXT = "xxxx";

  @Test
  void canonicalConstructorShouldReturnStandardResult() {
    final var actual = new ShamsiDeath(YEAR_687, YEAR_687);
    assertThat(actual.year()).isEqualTo(YEAR_687);
    assertThat(actual.gregorian()).isEqualTo(YEAR_687);
  }

  @Test
  void canonicalConstructorShouldStripNonNumericText() {
    final var actual = new ShamsiDeath(GENERIC_TEXT + YEAR_687, GENERIC_TEXT + YEAR_687);
    assertThat(actual.year()).isEqualTo(GENERIC_TEXT + YEAR_687);
    assertThat(actual.gregorian()).isEqualTo(YEAR_687);
  }
}
