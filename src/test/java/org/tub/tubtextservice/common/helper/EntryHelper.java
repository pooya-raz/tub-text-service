package org.tub.tubtextservice.common.helper;

import java.util.List;
import org.tub.tubtextservice.domain.Commentary;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.domain.Manuscript;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.domain.person.Author;
import org.tub.tubtextservice.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.domain.year.persondate.ShamsiDeath;

public class EntryHelper {

    public static final long timeStamp = -2051222400L;

    private EntryHelper() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static List<TubEntry> createEntries() {
        final var author = new Author("Author", new HijriDeath("1323", "1905"));

        final var manuscript =
                new Manuscript("Majlis", "Tehran", "1195", new HijriDate("11th century", "17th century"), 1699);
        final var edition = new Edition(
                "Title Transliterated",
                "Title Arabic",
                new ShamsiDate("1388", "2009-10"),
                null,
                "Bustān-i Kitāb",
                null,
                null,
                "Tehran",
                2009);
        final var commentary = new Commentary(
                "Title Transliterated Commentary", new Author("Author", new HijriDeath("1323", "1905")), -2051222400L);
        final var entry1 = new TubEntry(
                "Title Transliterated",
                "Title Arabic",
                author,
                List.of(manuscript),
                List.of(edition),
                List.of(commentary),
                timeStamp,
                TitleType.TREATISE);

        final var authorShamsi = new Author("AuthorShamsi", new ShamsiDeath("1323", "1905"));
        final var entry2 = new TubEntry(
                "Title Transliterated Shamsi",
                "Title Arabic",
                authorShamsi,
                List.of(),
                List.of(),
                List.of(),
                timeStamp,
                TitleType.TREATISE);

        final var entry3 = new TubEntry(
                "Title Transliterated Commentary",
                "Title Arabic",
                author,
                List.of(),
                List.of(),
                List.of(),
                timeStamp,
                TitleType.TREATISE);
        return List.of(entry1, entry2, entry3);
    }
}
