package org.tub.tubtextservice.markdown;

import static java.nio.file.Files.readString;
import static java.nio.file.Path.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.application.usecase.docx.markdownconverter.MarkdownConverter;
import org.tub.tubtextservice.domain.Commentary;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.domain.Manuscript;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.domain.person.Author;
import org.tub.tubtextservice.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;

@ExtendWith(MockitoExtension.class)
class MarkdownConverterTest {

  public static final String TITLE_TRANSLITERATED = "Title transliterated";
  public static final String TITLE_ARABIC = "Title Arabic";
  public static final Edition EDITION =
      new Edition(
          "Title",
          TITLE_ARABIC,
          new HijriDate("600", "1000"),
          "Editor",
          "Publisher",
          null,
          "Edited",
          "City",
          1000);
  public static final String AUTHOR = "Author";
  public static final Commentary COMMENTARY =
      new Commentary("Title", new Author(AUTHOR, new HijriDeath("600", "1000")), -30610224000L);
  public static final String HIJRI_DEATH = "436";
  public static final String GREGORIAN_DEATH = "1044";
  public static final Manuscript MANUSCRIPT =
      new Manuscript("Location", "City", "1", new HijriDate("600", "1000"), 1000);
  private static String layout;
  private MarkdownConverter markdownService;

  @BeforeAll
  static void setUpAll() throws IOException {
    layout = readString(of("src/test/resources/markdown/layout.md"));
  }

  private static TubEntry getEntry(TitleType titleType) {
    return new TubEntry(
        TITLE_TRANSLITERATED,
        TITLE_ARABIC,
        new Author(AUTHOR, new HijriDeath(HIJRI_DEATH, GREGORIAN_DEATH)),
        List.of(MANUSCRIPT, MANUSCRIPT),
        List.of(EDITION, EDITION),
        List.of(COMMENTARY, COMMENTARY),
        -29221732800L,
        titleType);
  }

  @BeforeEach
  void setUp() {
    markdownService = new MarkdownConverter();
  }

  @Test
  @DisplayName("should convert EntriesDTO to markdown")
  void test1() {
    final var actual =
        markdownService.convert(
            new EntriesDto(
                List.of(
                    getEntry(TitleType.MONOGRAPH),
                    getEntry(TitleType.COMMENTARY),
                    getEntry(TitleType.GLOSS),
                    getEntry(TitleType.MARGINNOTES),
                    getEntry(TitleType.TREATISE),
                    getEntry(TitleType.SUMMARY),
                    getEntry(TitleType.POEM),
                    getEntry(TitleType.REFUTATION),
                    getEntry(TitleType.TAQRIRAT))));
    assertThat(actual).isEqualTo(layout);
  }
}
