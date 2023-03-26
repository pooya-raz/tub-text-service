package org.tub.tubtextservice.domain.person;

import org.tub.tubtextservice.domain.year.persondate.PersonDeath;

public record Author(String name, PersonDeath personDeath) implements Person {}
