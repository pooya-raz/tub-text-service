package org.tub.tubtextservice.service.markdown.layout;

import static java.nio.file.Files.readString;
import static java.nio.file.Path.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LayoutCreatorTest {

  private LayoutCreator layoutCreator;

    @BeforeEach
    void setUp() {
    layoutCreator = new LayoutCreator();
    }

    @Test
    @DisplayName("Should return the correct layout")
    void test1()throws IOException {
        final var expected = readString(of("src/test/resources/markdown/layout.md"));
    final var actual = layoutCreator.create();
        assertEquals(expected, actual);
    }

}
