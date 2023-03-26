package org.tub.tubtextservice.domain.model.tubentry.year.persondate;

import org.tub.tubtextservice.domain.model.tubentry.year.Year;

public sealed interface PersonDeath extends Year permits HijriDeath, ShamsiDeath {
}
