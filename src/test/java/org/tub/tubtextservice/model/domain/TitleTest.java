package org.tub.tubtextservice.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TitleTest {

  @Test
  void shouldHaveDefaultCategory() {
    Title title = Title.builder().build();
    assertEquals(StatusOfPublication.UNKNOWN, title.statusOfPublication());
  }

  @Test
  void shouldHaveDefaultTitleType() {
    Title title = Title.builder().build();
    assertEquals(TitleType.Unknown, title.titleType());
  }
}
