package org.tub.tubtextservice.domain;

import java.util.List;
import org.tub.tubtextservice.domain.person.Author;
import org.tub.tubtextservice.domain.person.Person;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;

public record TubEntry(
        String titleTransliterated,
        String titleOriginal,
        Person person,
        List<Manuscript> manuscripts,
        List<Edition> editions,
        List<Commentary> commentaries,
        Long sortTimeStamp,
        TitleType titleType) {

    public TubEntry {
        if (person == null) {
            person = new Author("Unknown", new HijriDeath("Unknown", "Unknown"));
        }
        manuscripts = List.copyOf(manuscripts);
        editions = List.copyOf(editions);
    }
}
