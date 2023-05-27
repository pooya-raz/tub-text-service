package org.tub.tubtextservice.domain;

import org.tub.tubtextservice.domain.year.editiondate.EditionDate;

public record Manuscript(
    String location,
    String city,
    String manuscriptNumber,
    EditionDate date,
    int sortYearGregorian) {}
