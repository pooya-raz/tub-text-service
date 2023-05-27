package org.tub.tubtextservice.application.usecase.docx.markdownconverter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.tub.tubtextservice.domain.Commentary;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.domain.Manuscript;
import org.tub.tubtextservice.domain.year.editiondate.ShamsiDate;

class SubSectionFormat {
  public static final String NEWLINE_AND_FOUR_SPACES = "\n    ";

  private SubSectionFormat() {
    throw new UnsupportedOperationException(
        "SubSectionFormat is a utility class and cannot be instantiated");
  }

  static String createManuscripts(final List<Manuscript> manuscripts) {
    if (manuscripts.isEmpty()) {
      return "";
    }
    final var layout = "* %s, %s (#%s), dated %s/%s";
    final var manuscriptsMarkdown =
        manuscripts.stream()
            .map(
                m ->
                    layout.formatted(
                        m.location(),
                        m.city(),
                        m.manuscriptNumber(),
                        m.date().year(),
                        m.date().gregorian()))
            .collect(Collectors.joining(NEWLINE_AND_FOUR_SPACES));
    return createSubSectionMarkdown("**Principle Manuscripts**", manuscriptsMarkdown);
  }

  static String createEditions(final List<Edition> editions) {
    if (editions.isEmpty()) {
      return "";
    }
    final var editionsMarkdown =
        editions.stream()
            .filter(Objects::nonNull)
            .map(SubSectionFormat::createEdition)
            .collect(Collectors.joining(NEWLINE_AND_FOUR_SPACES));
    return createSubSectionMarkdown("**Editions**", editionsMarkdown);
  }

  static String createEdition(final Edition edition) {
    final var layout = "* *%s*%s (%s %s, %s)";
    var editor = "";
    var place = "n.plac.,";
    var date = "n.d.";
    var publisher = Optional.ofNullable(edition.publisher()).orElse("n.pub");

    if (edition.editor() != null && !edition.editor().isBlank()) {
      editor = ", ed. " + edition.editor();
    }
    if (edition.placeOfPublication() != null && !edition.placeOfPublication().isBlank()) {
      place = edition.placeOfPublication() + ":";
    }
    if (!edition.date().year().isEmpty()) {
      date = edition.date().year();
    }
    if (!edition.date().gregorian().isEmpty()) {
      date = edition.date().gregorian();
    }
    if (!edition.date().year().isEmpty() && !edition.date().gregorian().isEmpty()) {
      var sh = "";
      if (edition.date()instanceof ShamsiDate) sh = "SH";
      date = edition.date().year() + sh + "/" + edition.date().gregorian();
    }
    return layout.formatted(edition.titleTransliterated(), editor, place, publisher, date);
  }

  static String createCommentaries(final List<Commentary> commentaries) {
    if (commentaries.isEmpty()) {
      return "";
    }
    final var layout = "* %s, %s %s";
    final var commentaryMarkdown =
        commentaries.stream()
            .map(
                e ->
                    layout.formatted(
                        e.title(), e.author().name(), DateFormat.create(e.author().personDeath())))
            .collect(Collectors.joining(NEWLINE_AND_FOUR_SPACES));
    return createSubSectionMarkdown("**Commentaries**", commentaryMarkdown);
  }

  private static String createSubSectionMarkdown(String header, String body) {
    return "\n   " + header + NEWLINE_AND_FOUR_SPACES + body + "\n";
  }
}
