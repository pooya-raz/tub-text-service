package usecase.markdown;

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
import org.tub.tubtextservice.application.usecase.docx.MarkdownConverter;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.domain.person.Author;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;

@ExtendWith(MockitoExtension.class)
class MarkdownConverterTest {

  private static String layout;
  private MarkdownConverter markdownService;

  @BeforeAll
  static void setUpAll() throws IOException {
    layout = readString(of("src/test/resources/markdown/layout.md"));
  }

  @BeforeEach
  void setUp() {
    markdownService = new MarkdownConverter();
  }

  @Test
  @DisplayName("Should return the correct markdown for all sections")
  void convertShouldReturnMarkdown() {
    final var monograph =
        new TubEntry(
            "Title transliterated",
            "Title Arabic",
            new Author("Author", new HijriDeath("436", "1044")),
            List.of(),
            List.of(),
            TitleType.MONOGRAPH);
    final var actual = markdownService.convert(new EntriesDto(List.of(monograph)));
    assertThat(actual).isEqualTo(layout);
  }
}
