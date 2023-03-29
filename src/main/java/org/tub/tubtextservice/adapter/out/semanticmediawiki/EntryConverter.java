package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.TubPrintouts;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.MediaWikiDate;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.MediaWikiPageDetails;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts.AuthorPrintouts;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts.DatedPrintouts;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts.EditionPrintouts;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts.Printouts;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts.TitlePrintouts;
import org.tub.tubtextservice.domain.Commentary;
import org.tub.tubtextservice.domain.Edition;
import org.tub.tubtextservice.domain.Manuscript;
import org.tub.tubtextservice.domain.TitleType;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.domain.person.Author;
import org.tub.tubtextservice.domain.person.Person;
import org.tub.tubtextservice.domain.year.editiondate.EditionDate;
import org.tub.tubtextservice.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.domain.year.persondate.PersonDeath;
import org.tub.tubtextservice.domain.year.persondate.ShamsiDeath;

/** Converts a {@link TubPrintouts} to an {@link TubEntry}. */
@Component
class EntryConverter {

  /**
   * Converts Semantic Mediawiki's {@link Printouts} to the {@link TubEntry}. The {@link TubEntry}
   * is constructed from the categories in the TUB wiki which are held in the fields of {@link
   * TubPrintouts}.
   *
   * @param tubPrintouts the data that is retrieved from Semantic MediaWiki
   * @return an {@link TubEntry}
   */
  List<TubEntry> convert(final TubPrintouts tubPrintouts) {
    return tubPrintouts.titles().values().stream()
        .map(title -> convertTitle(title, tubPrintouts))
        .toList();
  }

  /**
   * Converts a {@link TitlePrintouts} to an {@link TubEntry}.
   *
   * @param title the title to convert
   * @param tubPrintouts the data that is retrieved from Semantic MediaWiki
   * @return Entry
   */
  private TubEntry convertTitle(TitlePrintouts title, final TubPrintouts tubPrintouts) {
    final var author = title.author().stream().findFirst().orElse(null);
    final var person = getPerson(tubPrintouts.authors(), author);
    final var titleName = title.titleTransliterated().stream().findFirst().orElse("");
    final var titleOriginal = title.titleArabic().stream().findFirst().orElse("");
    final var manuscripts = getManuscripts(tubPrintouts.manuscripts(), titleName);
    final var editions = getEditions(tubPrintouts.editions(), titleName);
    final List<Commentary> commentaries = getCommentaries(tubPrintouts, titleName);
    final var titleType =
        TitleType.valueOfTub(title.bookType().stream().findFirst().orElse("Unknown"));
    return new TubEntry(
        titleName, titleOriginal, person, manuscripts, editions, commentaries, titleType);
  }

  private Person getPerson(
      final Map<String, AuthorPrintouts> authorPrintoutsMap,
      final MediaWikiPageDetails authorPage) {
    return Optional.ofNullable(authorPage)
        .map(MediaWikiPageDetails::fulltext)
        .map(authorPrintoutsMap::get)
        .map(this::convertDate)
        .map(personDeath -> new Author(authorPage.fulltext(), personDeath))
        .orElse(null);
  }

  private List<Manuscript> getManuscripts(
      final Map<String, ArrayList<ManuscriptPrintouts>> manuscriptPrintoutsMap,
      final String titleName) {
    return Optional.ofNullable(manuscriptPrintoutsMap.get(titleName))
        .map(printouts -> printouts.stream().map(this::convertManuscript).toList())
        .orElseGet(List::of);
  }

  private List<Edition> getEditions(
      final Map<String, ArrayList<EditionPrintouts>> editionPrintoutsMap, final String titleName) {
    return Optional.ofNullable(editionPrintoutsMap.get(titleName))
        .map(printouts -> printouts.stream().map(this::convertEdition).toList())
        .orElseGet(List::of);
  }

  private List<Commentary> getCommentaries(TubPrintouts tubPrintouts, final String title) {
    return Optional.ofNullable(tubPrintouts.commentaries())
        .map(commentaryMap -> commentaryMap.get(title))
        .map(
            printouts ->
                printouts.stream()
                    .map(commentary -> convertCommentary(commentary, tubPrintouts))
                    .toList())
        .orElseGet(List::of);
  }

  Commentary convertCommentary(
      final TitlePrintouts titlePrintouts, final TubPrintouts tubPrintouts) {
    final var authorKey = titlePrintouts.author().stream().findFirst().orElse(null);
    final var author = (Author) getPerson(tubPrintouts.authors(), authorKey);
    final var title = titlePrintouts.titleTransliterated().stream().findFirst().orElse("");

    return new Commentary(title, author);
  }

  Edition convertEdition(EditionPrintouts editionPrintouts) {
    return Edition.builder()
        .titleTransliterated(
            editionPrintouts.titleTransliterated().stream().findFirst().orElse(null))
        .titleArabic(editionPrintouts.titleArabic().stream().findFirst().orElse(null))
        .editor(editionPrintouts.editors().stream().findFirst().orElse(null))
        .publisher(editionPrintouts.publisher().stream().findFirst().orElse(null))
        .description(editionPrintouts.description().stream().findFirst().orElse(null))
        .editionType(editionPrintouts.editionType().stream().findFirst().orElse(null))
        .placeOfPublication(
            editionPrintouts.city().stream()
                .findFirst()
                .map(MediaWikiPageDetails::fulltext)
                .orElse(null))
        .date(convertDate(editionPrintouts))
        .build();
  }

  public Manuscript convertManuscript(ManuscriptPrintouts manuscriptPrintouts) {
    return new Manuscript(
        manuscriptPrintouts.location().stream().findFirst().orElse(null),
        manuscriptPrintouts.city().stream()
            .findFirst()
            .map(MediaWikiPageDetails::fulltext)
            .orElse(null),
        manuscriptPrintouts.manuscriptNumber().stream().findFirst().orElse(null),
        convertDate(manuscriptPrintouts));
  }

  private EditionDate convertDate(DatedPrintouts printouts) {
    final var hijri = getYear(printouts.yearHijri(), printouts.yearHijriText());
    final var shamsi = getYear(printouts.yearShamsi(), printouts.yearShamsiText());
    final var gregorian = getYear(printouts.yearGregorian(), printouts.yearGregorianText());

    if (!shamsi.isEmpty()) {
      return new ShamsiDate(shamsi, gregorian);
    }
    return new HijriDate(hijri, gregorian);
  }

  private PersonDeath convertDate(AuthorPrintouts source) {
    final var hijri = getYear(source.deathHijri(), source.deathHijriText());
    final var shamsi = getYear(source.deathShamsi(), source.deathShamsiText());
    final var gregorian =
        getYearFromMediaWikiDate(source.deathGregorian(), source.deathGregorianText());

    if (!shamsi.isEmpty()) {
      return new ShamsiDeath(shamsi, gregorian);
    }
    return new HijriDeath(hijri, gregorian);
  }

  private String getYear(List<Integer> year, List<String> yearText) {
    var result = "";
    if (!year.isEmpty() && year.get(0) != null) {
      result = year.get(0).toString();
    }
    if (!yearText.isEmpty()) {
      result = yearText.get(0);
    }
    return result;
  }

  private String getYearFromMediaWikiDate(
      List<MediaWikiDate> gregorian, List<String> gregorianText) {
    var year = "";

    if (!gregorian.isEmpty()) {
      year = convertMediaWikiDate(gregorian.get(0));
    }
    if (!gregorianText.isEmpty()) {
      year = gregorianText.get(0);
    }
    return year;
  }

  private String convertMediaWikiDate(MediaWikiDate source) {
    final var date = LocalDate.ofEpochDay(source.timestamp() / 86400).getYear();
    return String.valueOf(date);
  }
}
