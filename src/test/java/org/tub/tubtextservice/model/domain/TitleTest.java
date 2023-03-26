package org.tub.tubtextservice.model.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.domain.StatusOfPublication;
import org.tub.tubtextservice.domain.Title;
import org.tub.tubtextservice.domain.TitleType;

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
