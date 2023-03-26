package org.tub.tubtextservice.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tub.tubtextservice.common.NonNumericTextStripper.stripNonNumericText;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NonNumericTextStripperTest {

  public static final String EXEPECTED = "687";

  @Test
  @DisplayName("Remove leading text")
  void stripNonNumericTextShouldRemoveLeadingText() {
    final var actual = stripNonNumericText("xxxx" + EXEPECTED);
    assertThat(actual).isEqualTo(EXEPECTED);
  }

  @Test
  @DisplayName("Don't remove text after digits")
  void stripNonNumericTextShouldNotRemoveTextAfterFirstDigit() {
    final var expected = EXEPECTED + "xxxx";
    final var actual = stripNonNumericText(expected);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("Don't remove text if no non-numeric chars")
  void stripNonNumericTextShouldNotRemoveTextIfNoNonNumericChars() {
    final var actual = stripNonNumericText(EXEPECTED);
    assertThat(actual).isEqualTo(EXEPECTED);
  }

  @Test
  @DisplayName("If no digits should return text")
  void stripNonNumericTextShouldReturnText(){
    final var actual = stripNonNumericText("n.d.");
    assertThat(actual).isEqualTo("n.d.");
  }
}
