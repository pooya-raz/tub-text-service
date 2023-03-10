package org.tub.tubtextservice.service.tubdata.converter;

import org.springframework.stereotype.Component;
import org.tub.tubtextservice.model.domain.Edition;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.domain.Manuscript;
import org.tub.tubtextservice.model.domain.TitleType;
import org.tub.tubtextservice.model.domain.person.Author;
import org.tub.tubtextservice.model.domain.person.Person;
import org.tub.tubtextservice.service.tubdata.model.TubPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.TitlePrintouts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Converts a {@link TubPrintouts} to a {@link Entry}. */
@Component
public class EntryConverter {

  private final ManuscriptConverter manuscriptConverter;
  private final TubDateConverter tubDateConverter;

  private final EditionConverter editionConverter;

  public EntryConverter(
      ManuscriptConverter manuscriptConverter,
      TubDateConverter tubDateConverter,
      EditionConverter editionConverter) {
    this.manuscriptConverter = manuscriptConverter;
    this.tubDateConverter = tubDateConverter;
    this.editionConverter = editionConverter;
  }

  /**
   * Converts Semantic Mediawiki's domain model {@link Printouts} to the domain model {@link Entry}.
   * The {@link Entry} is constructed from the categories in the TUB wiki which are held in the
   * fields of {@link TubPrintouts}.
   *
   * @param tubPrintouts the data that is retrieved from Semantic MediaWiki
   * @return an {@link Entry}
   */
  public List<Entry> convert(final TubPrintouts tubPrintouts) {
    return tubPrintouts.titles().values().stream()
        .map(title -> convertTitle(title, tubPrintouts))
        .toList();
  }

  /**
   * Converts a {@link TitlePrintouts} to an {@link Entry}.
   *
   * @param title the title to convert
   * @param tubPrintouts the data that is retrieved from Semantic MediaWiki
   * @return Entry
   */
  private Entry convertTitle(TitlePrintouts title, final TubPrintouts tubPrintouts) {
    final var person = getPerson(tubPrintouts.authors(), title.author().get(0));
    final var titleName = title.titleTransliterated().stream().findFirst().orElse("");
    final var titleOriginal = title.titleArabic().stream().findFirst().orElse("");
    final var manuscripts = getManuscripts(tubPrintouts.manuscripts(), titleName);
    final var editions = getEditions(tubPrintouts.editions(), titleName);
    final var titleType =
        TitleType.valueOfTub(title.bookType().stream().findFirst().orElse("Unknown"));
    return new Entry(titleName, titleOriginal, person, manuscripts, editions, titleType);
  }

  private Person getPerson(
      final Map<String, AuthorPrintouts> authorPrintoutsMap,
      final MediaWikiPageDetails authorPage) {
    return Optional.ofNullable(authorPage)
        .map(MediaWikiPageDetails::fulltext)
        .map(authorPrintoutsMap::get)
        .map(tubDateConverter::convert)
        .map(personDeath -> new Author(authorPage.fulltext(), personDeath))
        .orElse(null);
  }

  private List<Manuscript> getManuscripts(
      final Map<String, ArrayList<ManuscriptPrintouts>> manuscriptPrintoutsMap,
      final String titleName) {
    return Optional.ofNullable(manuscriptPrintoutsMap.get(titleName))
        .map(printouts -> printouts.stream().map(manuscriptConverter::convert).toList())
        .orElseGet(List::of);
  }

  private List<Edition> getEditions(
      final Map<String, ArrayList<EditionPrintouts>> editionPrintoutsMap, final String titleName) {
    return Optional.ofNullable(editionPrintoutsMap.get(titleName))
        .map(printouts -> printouts.stream().map(editionConverter::convert).toList())
        .orElseGet(List::of);
  }
}
