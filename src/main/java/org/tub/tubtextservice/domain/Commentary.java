package org.tub.tubtextservice.domain;

import org.tub.tubtextservice.domain.person.Author;

public record Commentary(
        String title,
        Author author
) {}
