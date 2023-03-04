package org.tub.tubtextservice.model.domain;

import org.tub.tubtextservice.model.domain.person.Person;

import java.util.List;

public record Entry(
    String titleTransliterated,
    String titleOriginal,
    Person person,
    List<Manuscript> manuscripts,
    List<Edition> editions,
    TitleType titleType) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String titleTransliterated;
    private String titleOriginal;
    private Person person;
    private List<Manuscript> manuscripts;
    private List<Edition> editions;
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

    public Builder manuscripts(List<Manuscript> manuscripts) {
      this.manuscripts = manuscripts;
      return this;
    }

    public Builder editions(List<Edition> editions) {
      this.editions = editions;
      return this;
    }

    public Builder titleType(TitleType titleType) {
      this.titleType = titleType;
      return this;
    }

    public Entry build() {
      return new Entry(
          titleTransliterated, titleOriginal, person, manuscripts, editions, titleType);
    }
  }
}
