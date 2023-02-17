package org.tub.tubtextservice.service.tubapi.converter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.model.domain.Edition;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.domain.Manuscript;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.model.domain.person.Author;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.service.tubapi.model.TubPrintOuts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class EntryConverterTest {

  private final EntryConverter entryConverter = new EntryConverter();

  @Test
  void convertShouldReturnEntry() {
    final var title =
        TitlePrintouts.builder()
            .titleTransliterated("titleTransliterated")
            .category(MediaWikiPageDetails.builder().fulltext("Edited").build())
            .titleArabic("titleArabic")
            .bookType("Monograph")
            .author("author")
            .build();
    final var manuscriptPrintout =
        ManuscriptPrintouts.builder()
            .manuscriptOfTitle("titleTransliterated")
            .city("city")
            .location("location")
            .yearHijri(687)
            .yearGregorian(1288)
            .build();
    final var editionPrintout =
        EditionPrintouts.builder()
            .publishedEditionOfTitle("titleTransliterated")
            .titleTransliterated("titleTransliterated")
            .yearHijri(687)
            .yearGregorian(1288)
            .build();
    final var authorPrintout =
        AuthorPrintouts.builder()
            .deathHijri(687)
            .deathGregorian(1288L)
            .fullNameTransliterated("author")
            .build();
    final var mapAuthorPrintout = Map.of("author", authorPrintout);
    final var mapManuscriptPrintout = Map.of("titleTransliterated", manuscriptPrintout);
    final var mapEditionPrintout = Map.of("titleTransliterated", editionPrintout);
    final var printouts =
        new TubPrintOuts(
            List.of(title), mapAuthorPrintout, mapManuscriptPrintout, mapEditionPrintout);
    final var actual = entryConverter.convert(title, printouts);
    final var expected =
        new Entry(
            "titleTransliterated",
            "titleArabic",
            new Author("author", new HijriDeath("687", "1288")),
            List.of(
                new Manuscript(
                    "titleTransliterated", "city", "location", new HijriDate("684", "1288"))),
            List.of(new Edition("titleTransliterated", null, null, null, null, null, null, null)),
            TitleType.Monograph);
    assertThat(actual).isEqualTo(expected);
  }
}
