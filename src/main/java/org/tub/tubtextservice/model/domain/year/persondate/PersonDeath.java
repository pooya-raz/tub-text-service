package org.tub.tubtextservice.model.domain.year.persondate;

import org.tub.tubtextservice.model.domain.year.Year;

public sealed interface PersonDeath extends Year permits HijriDeath, ShamsiDeath {
}
