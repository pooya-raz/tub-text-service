package org.tub.tubtextservice.domain.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tub.tubtextservice.domain.common.NonNumericTextStripper.stripNonNumericText;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NonNumericTextStripperTest {

  public static final String EXEPECTED = "687";

  @Test
  @DisplayName("Remove leading text")
  void test1() {
    final var actual = stripNonNumericText("xxxx" + EXEPECTED);
    assertThat(actual).isEqualTo(EXEPECTED);
  }

  @Test
  @DisplayName("Don't remove text after digits")
  void test2() {
    final var expected = EXEPECTED + "xxxx";
    final var actual = stripNonNumericText(expected);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("Don't remove text if no non-numeric chars")
  void test3() {
    final var actual = stripNonNumericText(EXEPECTED);
    assertThat(actual).isEqualTo(EXEPECTED);
  }

  @Test
  @DisplayName("If no digits should return text")
  void test4(){
    final var actual = stripNonNumericText("n.d.");
    assertThat(actual).isEqualTo("n.d.");
  }
}