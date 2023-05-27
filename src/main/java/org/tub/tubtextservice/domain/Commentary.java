package org.tub.tubtextservice.domain;

import org.tub.tubtextservice.domain.person.Author;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;

public record Commentary(
        String title,
        Author author,
        Long sortTimeStamp
) {
    public Commentary {
        if (author == null){
      author = new Author("Unknown", new HijriDeath("Unknown", "Unknown"));
        }
    }
}
