package org.tub.tubtextservice.service.tubapi.service;

import org.springframework.stereotype.Service;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.service.tubapi.client.TubClient;
import org.tub.tubtextservice.service.tubapi.model.TubPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Data;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.TubResponse;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
/** The service responsible for retrieving data from the TUB API. */
@Service
public class TubApiService implements ApiService {

  private final TubClient tubClient;
  private final TubProperties properties;

  public TubApiService(TubClient tubClient, TubProperties properties) {
    this.tubClient = tubClient;
    this.properties = properties;
  }

  /**
   * Retrieves the data from the TUB API and returns a collection of maps required to construct the
   * {@link Entry}. It sends Semantic Mediawiki queries that are defined in the {@link
   * TubProperties}.
   *
   * @return {@link Printouts} from the TUB API as maps with the {@link Data#fullText()} as the key.
   */
  public TubPrintouts getData() {
    final var dataFetcher = new TubDataFetcher();
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
   * Responsible for creating a map with the {@link Data#fullText()} as the key and the {@link
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
     * @return map with the {@link Data#fullText()} as the key and the {@link TitlePrintouts} as the
     *     value
     */
    private static Map<String, TitlePrintouts> createMapTitles(List<Data> dataList) {
      final var mapTitle = new HashMap<String, TitlePrintouts>();
      dataList.forEach(
          data -> {
            final var printout = (TitlePrintouts) data.printouts();
            final var key = data.fullText();
            mapTitle.putIfAbsent(key, printout);
          });
      return mapTitle;
    }

    /**
     * Creates the required map for AuthorPrintouts.
     *
     * @param dataList the list of {@link Data} to be converted to a map.
     * @return map with the {@link Data#fullText()} as the key and the {@link TitlePrintouts} as the
     *     value
     */
    private static Map<String, AuthorPrintouts> createMapAuthor(List<Data> dataList) {
      final var mapTitle = new HashMap<String, AuthorPrintouts>();
      dataList.forEach(
          data -> {
            final var printout = (AuthorPrintouts) data.printouts();
            final var key = data.fullText();
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
            final var key = printout.manuscriptOfTitle().get(0).fulltext();
            addToMap(mapTitle, key, printout);
          });
      return mapTitle;
    }

    private static Map<String, ArrayList<EditionPrintouts>> createMapEdition(List<Data> dataList) {
      final var mapTitle = new HashMap<String, ArrayList<EditionPrintouts>>();
      dataList.forEach(
          data -> {
            final var printout = (EditionPrintouts) data.printouts();
            final var key = printout.editionOfTitle().get(0).fulltext();
            addToMap(mapTitle, key, printout);
          });
      return mapTitle;
    }

    /**
     * Adds the {@code printouts} to the {@code map} with the given {@code key}. Adds to the list if
     * the key already exists.
     *
     * @param map the map to add the {@link Printouts} to.
     * @param key must be {@link Data#fullText()}.
     * @param printouts the {@link Printouts} to add to the map.
     */
    private static <T extends Printouts> void addToMap(
        HashMap<String, ArrayList<T>> map, String key, T printouts) {
      if (!map.containsKey(key)) {
        final var list = new ArrayList<T>();
        list.add(printouts);
        map.put(key, list);
      } else {
        map.get(key).add(printouts);
      }
    }
  }

  /** Responsible fore retrieving the data from the TUB API. */
  private class TubDataFetcher {

    /** The stop value is used to stop the retrieval of data from the TUB API. */
    public static final int STOP = 0;

    /**
     * The offset is used to retrieve the data in batches. The offset is the number of items to
     * skip.
     */
    private int offset = STOP;

    /**
     * Concatenates the data from the TUB API that is returned in multiple requests.
     *
     * @param query the query as defined by Semantic MediaWiki.
     * @return the concatenated {@link Data} from the TUB API.
     */
    private List<Data> getAllData(final String query) {
      var list = fetchData(query);
      while (offset != STOP) {
        list =
            Stream.of(list, fetchData(query + "|offset=" + offset)).flatMap(List::stream).toList();
      }
      return list;
    }

    /**
     * Fetches the data from TUB API. The data is batched based on the value of the {@link
     * TubDataFetcher#offset}. The offset is initially set to 0 and is only updated if the response
     * contains a value for {@link TubResponse#queryContinueOffset()}.
     *
     * @param query the query as defined by Semantic MediaWiki.
     * @return the {@link Data} from the TUB API.
     */
    private List<Data> fetchData(final String query) {

      if (query == null) return List.of();
      final var result = tubClient.queryTub("ask", "json", query).block();
      return Optional.ofNullable(result)
          .map(
              r -> {
                offset = result.queryContinueOffset();
                return r.query().results().getDataMap().values().stream().toList();
              })
          .orElseGet(
              () -> {
                offset = STOP;
                return List.of();
              });
    }
  }
}
