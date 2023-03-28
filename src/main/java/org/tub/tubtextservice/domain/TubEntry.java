package org.tub.tubtextservice.domain;

import java.util.List;
import org.tub.tubtextservice.domain.person.Person;

public record TubEntry(
    String titleTransliterated,
    String titleOriginal,
    Person person,
    List<Manuscript> manuscripts,
    List<Edition> editions,

    List<Commentary> commentaries,
    TitleType titleType) {

  public TubEntry {
    manuscripts = List.copyOf(manuscripts);
    editions = List.copyOf(editions);
  }
}
