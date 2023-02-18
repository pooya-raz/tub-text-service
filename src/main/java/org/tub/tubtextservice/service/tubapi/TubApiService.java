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
  public static final int STOP = 0;
  private static final String ACTION_ASK = "ask";
  private static final String FORMAT_JSON = "json";
  private final TubClient tubClient;
  private final TubProperties tub;
  private int offset = STOP;

  public TubApiService(TubClient tubClient, TubProperties tub) {
    this.tubClient = tubClient;
    this.tub = tub;
  }

  /**
   * Retrieves the data from the TUB API. Separate queries are required for each category which are
   * organised as {@link Printouts}.
   *
   * @return the {@link Printouts} from the TUB API as maps with the {@link Data#fullText()} as they
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

  private <T extends Printouts> Map<String, ArrayList<T>> getMapPrintouts(
      String query, Class<T> printoutsClass) {
    final var data = getAllData(query);
    return createMap(data, printoutsClass);
  }

  /**
   * Creates a map with the {@link Data#fullText()} as the key and the {@link Printouts} as the
   * value.
   *
   * @param dataList the list of {@link Data} to be converted to a map.
   * @param printoutsClass the class of the {@link Printouts} to be cast to.
   * @return the map with the {@link Data#fullText()} as the key and the {@link Printouts} as the
   *     value.
   * @param <T> the type of {@link Printouts} to be cast to.
   */
  private <T extends Printouts> Map<String, ArrayList<T>> createMap(
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

  private <T extends Printouts> void addToMap(
      HashMap<String, ArrayList<T>> map, String key, T printout) {
    if (!map.containsKey(key)) {
      final var list = new ArrayList<T>();
      list.add(printout);
      map.put(key, list);
    } else {
      map.get(key).add(printout);
    }
  }

  /**
   * Concatenates the data from the TUB API that is returned in multiple requests.
   *
   * @param query the query as defined by Semantic MediaWiki.
   * @return the concatenated {@link Data} from the TUB API.
   */
  private List<Data> getAllData(final String query) {
    var list = fetchData(query);
    while (offset != STOP) {
      list = Stream.of(list, fetchData(query + "|offset=" + offset)).flatMap(List::stream).toList();
    }
    return list;
  }

  /**
   * Fetches the data from TUB API. The data is paginated based on the {@link TubApiService#offset}.
   * The offset is initial set to 0 and is updated with the value of returned by the TUB API in
   * {@link TubResponse#queryContinueOffset()}.
   *
   * @param query the query as defined by Semantic MediaWiki.
   * @return the {@link Data} from the TUB API.
   */
  private List<Data> fetchData(final String query) {
    if (query == null) return List.of();
    final var result = tubClient.queryTub(ACTION_ASK, FORMAT_JSON, query).block();
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
