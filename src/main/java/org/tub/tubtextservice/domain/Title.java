package org.tub.tubtextservice.domain;

import java.util.Objects;
import org.tub.tubtextservice.domain.person.Person;

public record Title(
    String titleTransliterated,
    String titleArabic,
    Person person,
    String description,
    StatusOfPublication statusOfPublication,
    TitleType titleType,
    String baseText) {
  public Title {
    if (Objects.isNull(titleType)) {
      titleType = TitleType.UNKNOWN;
    }
    if (Objects.isNull(statusOfPublication)) {
      statusOfPublication = StatusOfPublication.UNKNOWN;
    }
  }
}
