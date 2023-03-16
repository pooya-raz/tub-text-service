package org.tub.tubtextservice.helper;

import java.util.List;
import org.tub.tubtextservice.model.domain.Edition;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.domain.Manuscript;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.model.domain.person.Author;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;

public class EntryHelper {

    private EntryHelper() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

  public static Entry createEntry() {
      final var author = new Author("Author", new HijriDeath("1323", "1905"));
      final var manuscript = new Manuscript("Majlis", "Tehran", "1195",new HijriDate("11th century", "17th century"));
      final var edition = new Edition("Title Transliterated", "Title Arabic", new ShamsiDate("1388","2009-10"),null, "Bustān-i Kitāb", null, null,"Tehran");
      return new Entry(
                      "Title Transliterated",
                      "Title Arabic",
                      author,
                      List.of(manuscript),
                      List.of(edition),
                      TitleType.TREATISE);
    }
}
