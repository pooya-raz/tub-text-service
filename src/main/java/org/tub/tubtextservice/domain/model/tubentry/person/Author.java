package org.tub.tubtextservice.domain.model.tubentry.person;

import org.tub.tubtextservice.domain.model.tubentry.year.persondate.PersonDeath;

public record Author(String name, PersonDeath personDeath) implements Person {}
