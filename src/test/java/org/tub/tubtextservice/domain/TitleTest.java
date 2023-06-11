package org.tub.tubtextservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TitleTest {

    @Test
    void shouldHaveDefaultCategory() {
        final var title = new Title(null, null, null, null, null, null, null);
        assertEquals(StatusOfPublication.UNKNOWN, title.statusOfPublication());
    }

    @Test
    void shouldHaveDefaultTitleType() {
        final var title = new Title(null, null, null, null, null, null, null);
        assertEquals(TitleType.UNKNOWN, title.titleType());
    }
}
