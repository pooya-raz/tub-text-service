package org.tub.tubtextservice.model.domain;

import org.tub.tubtextservice.model.domain.person.Author;

public record Entry(String titleTransliterated, String titleArabic, Author author) {}
