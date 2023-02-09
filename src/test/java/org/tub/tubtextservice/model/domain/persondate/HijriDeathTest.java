package org.tub.tubtextservice.model.domain.persondate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HijriDeathTest {
  public static final String GENERIC_687 = "687";
  public static final String GENERIC_TEXT = "xxxx";

  @Test
  void canonicalConstructorShouldReturnStandardResult() {
    final var actual = new HijriDeath(GENERIC_687, GENERIC_687);
    assertThat(actual.hijri()).isEqualTo(GENERIC_687);
    assertThat(actual.gregorian()).isEqualTo(GENERIC_687);
  }

  @Test
  void canonicalConstructorShouldStripNonNumericText() {
    final var actual = new HijriDeath(GENERIC_TEXT + GENERIC_687, GENERIC_TEXT + GENERIC_687);
    assertThat(actual.hijri()).isEqualTo(GENERIC_TEXT + GENERIC_687);
    assertThat(actual.gregorian()).isEqualTo(GENERIC_687);
  }
}
