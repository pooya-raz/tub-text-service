package org.tub.tubtextservice.model.domain.person;

import org.tub.tubtextservice.model.domain.persondate.PersonDeath;

public record Author(String name, PersonDeath personDeath) implements Person {}
