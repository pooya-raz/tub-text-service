package org.tub.tubtextservice.application.usecase.markdownconverter;

import java.util.List;
import java.util.stream.Collectors;
import org.tub.tubtextservice.domain.Commentary;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.domain.Manuscript;

class SubSectionFormat{
  static final String END_SECTION = "\n    ";
    private SubSectionFormat() {
        throw new UnsupportedOperationException("SubSectionFormat is a utility class and cannot be instantiated");
    }

  static String createManuscripts(final List<Manuscript> manuscripts) {
    if (manuscripts.isEmpty()){
      return "";
    }
    final var layout = "* %s, %s (#%s), dated %s/%s";
    return manuscripts.stream()
        .map(
            m ->
                layout.formatted(
                    m.location(),
                    m.city(),
                    m.manuscriptNumber(),
                    m.date().year(),
                    m.date().gregorian()))
        .collect(Collectors.joining(END_SECTION));
  }

  static String createEditions(final List<Edition> editions) {
    if (editions.isEmpty()){
      return "";
    }
    final var layout = "* %s, ed. %s (%s: %s, %s/%s)";
    return editions.stream()
        .map(
            e ->
                layout.formatted(
                    e.titleTransliterated(),
                    e.editor(),
                    e.placeOfPublication(),
                    e.publisher(),
                    e.date().year(),
                    e.date().gregorian()))
        .collect(Collectors.joining(END_SECTION));
  }

  static String createCommentaries(final List<Commentary> commentaries) {
    if (commentaries.isEmpty()){
      return "";
    }
    final var layout = "* %s, %s %s";
    return commentaries.stream()
        .map(
            e ->
                layout.formatted(
                    e.title(), e.author().name(), DateFormat.create(e.author().personDeath())))
        .collect(Collectors.joining(END_SECTION));
  }
}
