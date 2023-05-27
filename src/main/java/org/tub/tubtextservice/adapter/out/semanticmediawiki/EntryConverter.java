package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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

  private final EditionComparator editionComparator;
  private final ManuscriptComparator manuscriptComparator;
  private final CommentaryComparator commentaryComparator;

  EntryConverter(EditionComparator editionComparator, ManuscriptComparator manuscriptComparator, CommentaryComparator commentaryComparator) {
    this.editionComparator = editionComparator;
    this.manuscriptComparator = manuscriptComparator;
    this.commentaryComparator = commentaryComparator;
  }

  /**
   * Converts Semantic Mediawiki's {@link Printouts} to the {@link TubEntry}. The {@link TubEntry}
   * is constructed from the categories in the TUB wiki which are held in the fields of {@link
   * TubPrintouts}.
   *
   * @param tubPrintouts the data that is retrieved from Semantic MediaWiki
   * @return an {@link TubEntry}
   */
  ArrayList<TubEntry> convert(final TubPrintouts tubPrintouts) {
    return tubPrintouts.titles().values().stream()
        .map(title -> convertTitle(title, tubPrintouts))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private TubEntry convertTitle(TitlePrintouts title, final TubPrintouts tubPrintouts) {
    final var author = title.author().stream().findFirst().orElse(null);
    final var authorPrintouts =
        Optional.ofNullable(author)
            .map(MediaWikiPageDetails::fulltext)
            .map(a -> tubPrintouts.authors().get(a))
            .orElse(null);
    final var person = getPerson(authorPrintouts);
    final var sortTimeStamp = getSortTimeStamp(authorPrintouts);
    final var titleName = title.titleTransliterated().stream().findFirst().orElse("");
    final var titleOriginal = title.titleArabic().stream().findFirst().orElse("");
    final var manuscripts = getManuscripts(tubPrintouts.manuscripts(), titleName);
    final var editions = getEditions(tubPrintouts.editions(), titleName);
    final var commentaries = getCommentaries(tubPrintouts, titleName);
    final var titleType =
        TitleType.valueOfTub(title.bookType().stream().findFirst().orElse("Unknown"));

    editions.sort(editionComparator);
    manuscripts.sort(manuscriptComparator);
    commentaries.sort(commentaryComparator);
    return new TubEntry(
        titleName,
        titleOriginal,
        person,
        manuscripts,
        editions,
        commentaries,
        sortTimeStamp,
        titleType);
  }

  private Long getSortTimeStamp(AuthorPrintouts source) {
    return Optional.ofNullable(source)
        .map(AuthorPrintouts::deathGregorian)
        .map(list -> list.stream().findFirst().orElse(new MediaWikiDate(Long.MAX_VALUE, null)))
        .map(MediaWikiDate::timestamp)
        .orElse(Long.MAX_VALUE);
  }

  private Person getPerson(final AuthorPrintouts authorPrintouts) {
    return Optional.ofNullable(authorPrintouts)
        .map(this::convertDate)
        .map(
            personDeath -> new Author(authorPrintouts.fullNameTransliterated().get(0), personDeath))
        .orElse(null);
  }

  private ArrayList<Manuscript> getManuscripts(
      final Map<String, ArrayList<ManuscriptPrintouts>> manuscriptPrintoutsMap,
      final String titleName) {
    return Optional.ofNullable(manuscriptPrintoutsMap.get(titleName))
        .map(
            printouts ->
                printouts.stream()
                    .map(this::convertManuscript)
                    .collect(Collectors.toCollection(ArrayList::new)))
        .orElseGet(ArrayList::new);
  }

  private ArrayList<Edition> getEditions(
      final Map<String, ArrayList<EditionPrintouts>> editionPrintoutsMap, final String titleName) {
    return Optional.ofNullable(editionPrintoutsMap.get(titleName))
        .map(
            printouts ->
                printouts.stream()
                    .map(this::convertEdition)
                    .collect(Collectors.toCollection(ArrayList::new)))
        .orElseGet(ArrayList::new);
  }

  private ArrayList<Commentary> getCommentaries(TubPrintouts tubPrintouts, final String title) {
    return Optional.ofNullable(tubPrintouts.commentaries())
        .map(commentaryMap -> commentaryMap.get(title))
        .map(
            printouts ->
                printouts.stream()
                    .map(commentary -> convertCommentary(commentary, tubPrintouts))
                    .collect(Collectors.toCollection(ArrayList::new)))
        .orElseGet(ArrayList::new);
  }

  private Commentary convertCommentary(
      final TitlePrintouts titlePrintouts, final TubPrintouts tubPrintouts) {
    final var authorKey = titlePrintouts.author().stream().findFirst().orElse(null);
    final var authorPrintouts =
        Optional.ofNullable(authorKey)
            .map(MediaWikiPageDetails::fulltext)
            .map(a -> tubPrintouts.authors().get(a))
            .orElse(null);
    final var author = (Author) getPerson(authorPrintouts);
    final var title = titlePrintouts.titleTransliterated().stream().findFirst().orElse("");
    final var sortTimeStamp = getSortTimeStamp(authorPrintouts);
    return new Commentary(title, author, sortTimeStamp);
  }

  private Edition convertEdition(EditionPrintouts editionPrintouts) {
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
        .sortYearGregorian(
            editionPrintouts.yearGregorian().stream().findFirst().orElse(Integer.MAX_VALUE))
        .build();
  }

  private Manuscript convertManuscript(ManuscriptPrintouts manuscriptPrintouts) {
    return new Manuscript(
        manuscriptPrintouts.location().stream().findFirst().orElse(null),
        manuscriptPrintouts.city().stream()
            .findFirst()
            .map(MediaWikiPageDetails::fulltext)
            .orElse(null),
        manuscriptPrintouts.manuscriptNumber().stream().findFirst().orElse(null),
        convertDate(manuscriptPrintouts),
        manuscriptPrintouts.yearGregorian().stream().findFirst().orElse(Integer.MAX_VALUE));
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
