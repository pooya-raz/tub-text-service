package org.tub.tubtextservice.model.domain.persondate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

  @Test
  void canonicalConstructorShouldThrowExceptionWhenGregorianYearIsEmpty() {
    assertThatThrownBy(() -> new HijriDeath(GENERIC_687, ""))
        .isInstanceOf(IllegalArgumentException.class);
  }
  @Test
    void canonicalConstructorShouldThrowExceptionWhenGregorianYearIsNull() {
        assertThatThrownBy(() -> new HijriDeath(GENERIC_687, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

  @Test
  void canonicalConstructorShouldThrowExceptionWhenHijriYearIsEmpty() {
    assertThatThrownBy(() -> new HijriDeath("", GENERIC_687))
        .isInstanceOf(IllegalArgumentException.class);
  }
    @Test
        void canonicalConstructorShouldThrowExceptionWhenHijriYearIsNull() {
            assertThatThrownBy(() -> new HijriDeath(null, GENERIC_687))
                .isInstanceOf(IllegalArgumentException.class);
        }
}
