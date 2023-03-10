package org.tub.tubtextservice.service.tubdata.converter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.builder.AuthorPrintoutsBuilder;
import org.tub.tubtextservice.builder.EditionPrintoutsBuilder;
import org.tub.tubtextservice.builder.ManuscriptPrintoutsBuilder;
import org.tub.tubtextservice.builder.TitlePrintoutsBuilder;
import org.tub.tubtextservice.model.domain.Edition;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.domain.Manuscript;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.model.domain.person.Author;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.service.tubdata.model.TubPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.TitlePrintouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntryConverterTest {
  public static final String TITLE_TRANSLITERATED = "titleTransliterated";
  public static final String TITLE_ARABIC = "titleArabic";
  public static final String YEAR = "687";
  public static final String GREGORIAN = "1288";
  private final TitlePrintouts TITLE =
      TitlePrintoutsBuilder.builder()
          .titleTransliterated(TITLE_TRANSLITERATED)
          .category("Edited")
          .titleArabic(TITLE_ARABIC)
          .bookType("Monograph")
          .author("author")
          .build();
  private final ManuscriptPrintouts MANUSCRIPT_PRINTOUT =
      ManuscriptPrintoutsBuilder.builder()
          .manuscriptOfTitle(TITLE_TRANSLITERATED)
          .city("city")
          .location("location")
          .yearHijri(687)
          .yearGregorian(1288)
          .build();
  private final EditionPrintouts EDITION_PRINTOUT =
      EditionPrintoutsBuilder.builder()
          .publishedEditionOfTitle(TITLE_TRANSLITERATED)
          .titleTransliterated(TITLE_TRANSLITERATED)
          .yearHijri(687)
          .yearGregorian(1288)
          .build();
  private final AuthorPrintouts AUTHOR_PRINTOUT =
      AuthorPrintoutsBuilder.builder()
          .deathHijri(687)
          .deathGregorian(1288L)
          .fullNameTransliterated("author")
          .build();
  private final Author AUTHOR = new Author("author", new HijriDeath(YEAR, GREGORIAN));
  private final Manuscript MANUSCRIPT =
      new Manuscript(TITLE_TRANSLITERATED, "city", "location", new HijriDate(YEAR, GREGORIAN));
  private final Edition EDITION =
      new Edition(TITLE_TRANSLITERATED, null, null, null, null, null, null, null);
  private final Map<String, AuthorPrintouts> mapAuthorPrintout = new HashMap<>();
  private final Map<String, ArrayList<ManuscriptPrintouts>> mapManuscriptPrintout = new HashMap<>();
  private final Map<String, ArrayList<EditionPrintouts>> mapEditionPrintout = new HashMap<>();
  private final Map<String, TitlePrintouts> titlePrintouts = new HashMap<>();
  private EntryConverter subject;
  @Mock private ManuscriptConverter manuscriptConverter;
  @Mock private TubDateConverter tubDateConverter;
  @Mock private EditionConverter editionConverter;
  private TubPrintouts printouts;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new EntryConverter(manuscriptConverter, tubDateConverter, editionConverter);
    mapAuthorPrintout.put("author", AUTHOR_PRINTOUT);

    final var mapList = new ArrayList<ManuscriptPrintouts>();
    mapList.add(MANUSCRIPT_PRINTOUT);
    mapManuscriptPrintout.put(TITLE_TRANSLITERATED, mapList);

    final var editionList = new ArrayList<EditionPrintouts>();
    editionList.add(EDITION_PRINTOUT);
    mapEditionPrintout.put(TITLE_TRANSLITERATED, editionList);

    titlePrintouts.put(TITLE_TRANSLITERATED, TITLE);

    printouts =
        new TubPrintouts(
            titlePrintouts, mapAuthorPrintout, mapManuscriptPrintout, mapEditionPrintout);
  }

  @AfterEach
  void afterEach() {
    mapAuthorPrintout.clear();
    mapManuscriptPrintout.clear();
    mapEditionPrintout.clear();
  }

  @Test
  void convertShouldReturnEntry() {

    final var expected =
        new Entry(
            TITLE_TRANSLITERATED,
            TITLE_ARABIC,
            AUTHOR,
            List.of(MANUSCRIPT),
            List.of(EDITION),
            TitleType.MONOGRAPH);

    when(manuscriptConverter.convert(MANUSCRIPT_PRINTOUT)).thenReturn(MANUSCRIPT);
    when(tubDateConverter.convert(AUTHOR_PRINTOUT)).thenReturn(new HijriDeath(YEAR, GREGORIAN));
    when(editionConverter.convert(EDITION_PRINTOUT)).thenReturn(EDITION);
    final var actual = subject.convert(printouts);
    assertThat(actual).isEqualTo(List.of(expected));
  }

  @Test
  void convertShouldReturnEntryForMultipleEditions() {

    mapEditionPrintout.get(TITLE_TRANSLITERATED).add(EDITION_PRINTOUT);

    final var expected =
        new Entry(
            TITLE_TRANSLITERATED,
            TITLE_ARABIC,
            AUTHOR,
            List.of(MANUSCRIPT),
            List.of(EDITION, EDITION),
            TitleType.MONOGRAPH);

    when(manuscriptConverter.convert(MANUSCRIPT_PRINTOUT)).thenReturn(MANUSCRIPT);
    when(tubDateConverter.convert(AUTHOR_PRINTOUT)).thenReturn(new HijriDeath(YEAR, GREGORIAN));
    when(editionConverter.convert(EDITION_PRINTOUT)).thenReturn(EDITION);
    final var actual = subject.convert(printouts);
    assertThat(actual).isEqualTo(List.of(expected));
  }

  @Test
  void convertShouldReturnEntryForMultipleManuscripts() {

    mapManuscriptPrintout.get(TITLE_TRANSLITERATED).add(MANUSCRIPT_PRINTOUT);

    final var expected =
        new Entry(
            TITLE_TRANSLITERATED,
            TITLE_ARABIC,
            AUTHOR,
            List.of(MANUSCRIPT, MANUSCRIPT),
            List.of(EDITION),
            TitleType.MONOGRAPH);

    when(manuscriptConverter.convert(MANUSCRIPT_PRINTOUT)).thenReturn(MANUSCRIPT);
    when(tubDateConverter.convert(AUTHOR_PRINTOUT)).thenReturn(new HijriDeath(YEAR, GREGORIAN));
    when(editionConverter.convert(EDITION_PRINTOUT)).thenReturn(EDITION);
    final var actual = subject.convert(printouts);
    assertThat(actual).isEqualTo(List.of(expected));
  }

  @Test
  void convertShouldReturnEntryWithoutEditionsOrManuscripts() {
    mapManuscriptPrintout.clear();
    mapEditionPrintout.clear();

    final var expected =
        new Entry(
            TITLE_TRANSLITERATED, TITLE_ARABIC, AUTHOR, List.of(), List.of(), TitleType.MONOGRAPH);

    when(tubDateConverter.convert(AUTHOR_PRINTOUT)).thenReturn(new HijriDeath(YEAR, GREGORIAN));
    final var actual = subject.convert(printouts);
    assertThat(actual).isEqualTo(List.of(expected));
  }
}
