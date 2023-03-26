package org.tub.tubtextservice.service.markdown;

import static java.nio.file.Files.readString;
import static java.nio.file.Path.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.model.domain.person.Author;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;

@ExtendWith(MockitoExtension.class)
class TubMarkdownServiceTest {

  private static String layout;
  TubMarkdownService markdownService;
  @Mock SectionCreator sectionCreator;

  @BeforeEach
  void setUp() throws IOException {
    markdownService = new TubMarkdownService(sectionCreator);
    layout = readString(of("src/test/resources/markdown/layout.md"));
  }

  @Test
  @DisplayName("Should return the correct markdown for all sections")
  void convertShouldReturnMarkdown() throws IOException {
    final var expected = finalMarkdown();
    final var monograph =
        new Entry(
            "Title",
            "Title Arabic",
            new Author("Author", new HijriDeath("600", "1200")),
            List.of(),
            List.of(),
            TitleType.MONOGRAPH);
    when(sectionCreator.create(List.of(monograph), TitleType.MONOGRAPH)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.TREATISE)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.COMMENTARY)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.MARGINNOTES)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.GLOSS)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.REFUTATION)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.POEM)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.SUMMARY)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.TAQRIRAT)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.TRANSLATION)).thenReturn("more");
    when(sectionCreator.create(List.of(monograph), TitleType.UNKNOWN)).thenReturn("more");

    final var actual = markdownService.createText(List.of(monograph));
    assertEquals(expected, actual);
  }

  private String finalMarkdown() throws IOException {
    return layout.formatted(
        "Title",
        "Title Arabic",
        "Author",
        "600",
        "1200",
        "more",
        "more",
        "more",
        "more",
        "more",
        "more");
  }
}
