package usecase.markdown;

import static java.nio.file.Files.readString;
import static java.nio.file.Path.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
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

  @BeforeEach
  void setUp() throws IOException {
    markdownService = new MarkdownConverter();
    layout = readString(of("src/test/resources/markdown/layout.md"));
  }

  @Test
  @DisplayName("Should return the correct markdown for all sections")
  void convertShouldReturnMarkdown() throws IOException {
    final var expected = finalMarkdown();
    final var monograph =
        new TubEntry(
            "Title",
            "Title Arabic",
            new Author("Author", new HijriDeath("600", "1200")),
            List.of(),
            List.of(),
            TitleType.MONOGRAPH);
    final var actual = markdownService.convert(new EntriesDto(List.of(monograph)));
    assertThat(actual).isEqualTo(expected);
  }

  private String finalMarkdown() {
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
