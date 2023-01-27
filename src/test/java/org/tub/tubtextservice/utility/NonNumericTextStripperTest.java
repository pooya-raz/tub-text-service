package org.tub.tubtextservice.utility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NonNumericTextStripperTest {

  public static final String EXEPECTED = "687";

  @Test
  void stripNonNumericTextShouldRemoveLeadingText() {
    final var actual = NonNumericTextStripper.stripNonNumericText("xxxx" + EXEPECTED);
    assertThat(actual).isEqualTo(EXEPECTED);
  }

  @Test
  void stripNonNumericTextShouldNotRemoveTextAfterFirstDigit() {
    final var expected = EXEPECTED + "xxxx";
    final var actual = NonNumericTextStripper.stripNonNumericText(expected);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void stripNonNumericTextShouldNotRemoveTextIfNoNonNumericChars() {
    final var actual = NonNumericTextStripper.stripNonNumericText(EXEPECTED);
    assertThat(actual).isEqualTo(EXEPECTED);
  }
}
