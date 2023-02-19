package org.tub.tubtextservice.service.tubapi;

import org.springframework.lang.NonNull;
import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.service.tubapi.client.TubClient;
import org.tub.tubtextservice.service.tubapi.model.TubPrintOuts;
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
public class TubApiService {

  private final TubClient tubClient;
  private final TubProperties tub;

  public TubApiService(TubClient tubClient, TubProperties tub) {
    this.tubClient = tubClient;
    this.tub = tub;
  }

  /**
   * Retrieves the data from the TUB API. Separate queries are required for each category which are
   * organised as {@link Printouts}.
   *
   * @return the {@link Printouts} from the TUB API as maps with the {@link Data#fullText()} as the
   *     key. The fulltext is the title of the Wiki article, and is used as a unique identifier by
   *     MediaWiki.
   */
  public TubPrintOuts getData() {
    final var titles = getMapPrintouts(tub.query().titles(), TitlePrintouts.class);
    final var authors = getMapPrintouts(tub.query().authors(), AuthorPrintouts.class);
    final var manuscripts = getMapPrintouts(tub.query().manuscripts(), ManuscriptPrintouts.class);
    final var editions = getMapPrintouts(tub.query().editions(), EditionPrintouts.class);

    return new TubPrintOuts(titles, authors, manuscripts, editions);
  }

  /**
   * Main workflow for retrieving the printouts from the TUB API. Gets the data from the TUB API and
   * then converts it to a map.
   *
   * @param query the Semantic MediaWiki query to be executed.
   * @param printoutsClass the class of the {@link Printouts} to be cast to.
   * @return a map of the {@link Printouts}. See {@link MapCreator}.
   */
  private <T extends Printouts> Map<String, ArrayList<T>> getMapPrintouts(
      String query, Class<T> printoutsClass) {
    final var dataFetcher = new TubDataFetcher();
    final var data = dataFetcher.getAllData(query);
    return MapCreator.createMap(data, printoutsClass);
  }

  /**
   * Creates a map with the {@link Data#fullText()} as the key and the {@link Printouts} as the
   * value
   */
  private static class MapCreator {
    /**
     * Creates a map with the {@link Data#fullText()} as the key and the {@link Printouts} as the
     * value.
     *
     * @param dataList the list of {@link Data} to be converted to a map.
     * @param printoutsClass the class of the {@link Printouts} to be cast to.
     * @return the map with the {@link Data#fullText()} as the key and the {@link Printouts} as the
     *     value.
     */
    private static <T extends Printouts> Map<String, ArrayList<T>> createMap(
        @NonNull final List<Data> dataList, @NonNull final Class<T> printoutsClass) {
      final var map = new HashMap<String, ArrayList<T>>();
      dataList.forEach(
          data -> {
            final var printout = printoutsClass.cast(data.printouts());
            final var key = data.fullText();
            addToMap(map, key, printout);
          });
      return map;
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

  /** Retrieves the data from the TUB API. */
  private class TubDataFetcher {

    /** The stop value is used to stop the retrieval of data from the TUB API. */
    public final int STOP = 0;

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
