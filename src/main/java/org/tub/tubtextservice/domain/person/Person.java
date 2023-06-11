package org.tub.tubtextservice.domain.person;

import org.tub.tubtextservice.domain.year.persondate.PersonDeath;

public sealed interface Person permits Author {
    String name();

    PersonDeath personDeath();
}
