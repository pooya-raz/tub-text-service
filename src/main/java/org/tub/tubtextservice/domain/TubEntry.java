package org.tub.tubtextservice.domain;

import java.util.Collection;
import java.util.List;
import org.tub.tubtextservice.domain.person.Person;

public record TubEntry(
    String titleTransliterated,
    String titleOriginal,
    Person person,
    List<Manuscript> manuscripts,
    List<Edition> editions,
    TitleType titleType) {

  public TubEntry {
    manuscripts = List.copyOf(manuscripts);
    editions = List.copyOf(editions);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String titleTransliterated;
    private String titleOriginal;
    private Person person;
    private List<Manuscript> manuscripts = List.of();
    private List<Edition> editions = List.of();
    private TitleType titleType;

    public Builder titleTransliterated(String titleTransliterated) {
      this.titleTransliterated = titleTransliterated;
      return this;
    }

    public Builder titleOriginal(String titleOriginal) {
      this.titleOriginal = titleOriginal;
      return this;
    }

    public Builder person(Person person) {
      this.person = person;
      return this;
    }

    public Builder manuscripts(Collection<Manuscript> manuscripts) {
      this.manuscripts = List.copyOf(manuscripts);
      return this;
    }

    public Builder editions(Collection<Edition> editions) {
      this.editions = List.copyOf(editions);
      return this;
    }

    public Builder titleType(TitleType titleType) {
      this.titleType = titleType;
      return this;
    }

    public TubEntry build() {
      return new TubEntry(
          titleTransliterated, titleOriginal, person, manuscripts, editions, titleType);
    }
  }
}
