package org.tub.tubtextservice.domain.year.persondate;

import org.tub.tubtextservice.domain.year.Year;

public sealed interface PersonDeath extends Year permits HijriDeath, ShamsiDeath {
    String year();
    String gregorian();
}
