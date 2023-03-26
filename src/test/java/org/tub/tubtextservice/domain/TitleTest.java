package org.tub.tubtextservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TitleTest {

  @Test
  void shouldHaveDefaultCategory() {
    Title title = Title.builder().build();
    assertEquals(StatusOfPublication.UNKNOWN, title.statusOfPublication());
  }

  @Test
  void shouldHaveDefaultTitleType() {
    Title title = Title.builder().build();
    assertEquals(TitleType.UNKNOWN, title.titleType());
  }
}
