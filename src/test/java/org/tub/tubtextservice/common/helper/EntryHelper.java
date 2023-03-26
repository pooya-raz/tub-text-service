package org.tub.tubtextservice.common.helper;

import java.util.List;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.domain.Manuscript;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.domain.person.Author;
import org.tub.tubtextservice.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;

public class EntryHelper {

    private EntryHelper() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

  public static TubEntry createEntry() {
      final var author = new Author("Author", new HijriDeath("1323", "1905"));
      final var manuscript = new Manuscript("Majlis", "Tehran", "1195",new HijriDate("11th century", "17th century"));
      final var edition = new Edition("Title Transliterated", "Title Arabic", new ShamsiDate("1388","2009-10"),null, "Bustān-i Kitāb", null, null,"Tehran");
      return new TubEntry(
                      "Title Transliterated",
                      "Title Arabic",
                      author,
                      List.of(manuscript),
                      List.of(edition),
                      TitleType.TREATISE);
    }
}
