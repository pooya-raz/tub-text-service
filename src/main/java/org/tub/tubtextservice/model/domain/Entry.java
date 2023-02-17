package org.tub.tubtextservice.model.domain;

import org.tub.tubtextservice.model.domain.person.Person;

import java.util.List;

public record Entry(
    String titleTransliterated,
    String titleOriginal,
    Person person,
    List<Manuscript> manuscripts,
    List<Edition> editions,
    TitleType titleType) {}
