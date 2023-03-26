package org.tub.tubtextservice.adapter.semanticmediawiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.property.TubProperties;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.Data;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.AuthorPrintouts;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.EditionPrintouts;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.Printouts;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts.TitlePrintouts;
import org.tub.tubtextservice.domain.TubEntry;

/** The service responsible for retrieving data from the TUB API. */
@Service
class PrintoutsManager {

  private final DataFetcher dataFetcher;
  private final TubProperties properties;

  PrintoutsManager(DataFetcher dataFetcher, TubProperties properties) {
    this.dataFetcher = dataFetcher;
    this.properties = properties;
  }

  /**
   * Retrieves the data from the TUB API and returns a collection of maps required to construct the
   * {@link TubEntry}. It sends Semantic Mediawiki queries that are defined in the {@link
   * TubProperties}.
   *
   * @return {@link Printouts} from the TUB API as maps with the {@link Data#fulltext()} as the key.
   */
  TubPrintouts getPrintouts() {
    final var titlePrintouts = dataFetcher.getAllData(properties.query().titles());
    final var authorPrintouts = dataFetcher.getAllData(properties.query().authors());
    final var manuscriptPrintouts = dataFetcher.getAllData(properties.query().manuscripts());
    final var editionPrintouts = dataFetcher.getAllData(properties.query().editions());

    final var titles = MapCreator.createMapTitles(titlePrintouts);
    final var authors = MapCreator.createMapAuthor(authorPrintouts);
    final var manuscripts = MapCreator.createMapManuscript(manuscriptPrintouts);
    final var editions = MapCreator.createMapEdition(editionPrintouts);
    return new TubPrintouts(titles, authors, manuscripts, editions);
  }

  /**
   * Responsible for creating a map with the {@link Data#fulltext()} as the key and the {@link
   * Printouts} as the value
   *
   * @implNote There are repetitive methods in this class. Couldn't think of a better way to do
   *     this.
   */
  private static class MapCreator {

    /**
     * Creates the required map for TitlePrintouts.
     *
     * @param dataList the list of {@link Data} to be converted to a map.
     * @return map with the {@link Data#fulltext()} as the key and the {@link TitlePrintouts} as the
     *     value
     */
    private static Map<String, TitlePrintouts> createMapTitles(List<Data> dataList) {
      final var mapTitle = new HashMap<String, TitlePrintouts>();
      dataList.forEach(
          data -> {
            final var printout = (TitlePrintouts) data.printouts();
            final var key = data.fulltext();
            mapTitle.putIfAbsent(key, printout);
          });
      return mapTitle;
    }

    /**
     * Creates the required map for AuthorPrintouts.
     *
     * @param dataList the list of {@link Data} to be converted to a map.
     * @return map with the {@link Data#fulltext()} as the key and the {@link TitlePrintouts} as the
     *     value
     */
    private static Map<String, AuthorPrintouts> createMapAuthor(List<Data> dataList) {
      final var mapTitle = new HashMap<String, AuthorPrintouts>();
      dataList.forEach(
          data -> {
            final var printout = (AuthorPrintouts) data.printouts();
            final var key = data.fulltext();
            mapTitle.putIfAbsent(key, printout);
          });
      return mapTitle;
    }

    private static Map<String, ArrayList<ManuscriptPrintouts>> createMapManuscript(
        List<Data> dataList) {
      final var mapTitle = new HashMap<String, ArrayList<ManuscriptPrintouts>>();
      dataList.forEach(
          data -> {
            final var printout = (ManuscriptPrintouts) data.printouts();
            var key = UUID.randomUUID().toString();
            if (printout.manuscriptOfTitle() != null && !printout.manuscriptOfTitle().isEmpty()) {
              key = printout.manuscriptOfTitle().get(0).fulltext();
            }
            addToMap(mapTitle, key, printout);
          });
      return mapTitle;
    }

    private static Map<String, ArrayList<EditionPrintouts>> createMapEdition(List<Data> dataList) {
      final var mapTitle = new HashMap<String, ArrayList<EditionPrintouts>>();
      dataList.forEach(
          data -> {
            final var printout = (EditionPrintouts) data.printouts();
            var key = UUID.randomUUID().toString();
            if (printout.editionOfTitle() != null && !printout.editionOfTitle().isEmpty()) {
              key = printout.editionOfTitle().get(0).fulltext();
            }
            addToMap(mapTitle, key, printout);
          });
      return mapTitle;
    }

    /**
     * Adds the {@code printouts} to the {@code map} with the given {@code key}. Adds to the list if
     * the key already exists.
     *
     * @param map the map to add the {@link Printouts} to.
     * @param key must be {@link Data#fulltext()}.
     * @param printouts the {@link Printouts} to add to the map.
     */
    private static <T extends Printouts> void addToMap(
        Map<String, ArrayList<T>> map, String key, T printouts) {
      if (!map.containsKey(key)) {
        final var list = new ArrayList<T>();
        list.add(printouts);
        map.put(key, list);
      } else {
        map.get(key).add(printouts);
      }
    }
  }
}
