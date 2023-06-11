package org.tub.tubtextservice.domain.year.editiondate;

import org.tub.tubtextservice.domain.year.Year;

public sealed interface EditionDate extends Year permits HijriDate, ShamsiDate {
    @Override
    String year();

    @Override
    String gregorian();
}
