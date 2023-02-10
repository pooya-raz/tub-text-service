package org.tub.tubtextservice.model.domain.persondate;

public sealed interface PersonDeath permits HijriDeath, ShamsiDeath {
    String year();
    String gregorian();
}
