package org.tub.tubtextservice.service.tubapi;

import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.service.tubapi.client.TubClient;
import org.tub.tubtextservice.service.tubapi.model.TubPrintOuts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Data;
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

/** The service for the TUB API. */
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
   * Gets the data from the TUB API.
   *
   * @return the data from the TUB API in the domain model.
   */
  public TubPrintOuts getData() {
    final var titles = getMapPrintouts(tub.query().titles(), TitlePrintouts.class);
    final var authors = getMapPrintouts(tub.query().authors(), AuthorPrintouts.class);
    final var manuscripts = getMapPrintouts(tub.query().manuscripts(), ManuscriptPrintouts.class);
    final var editions = getMapPrintouts(tub.query().editions(), EditionPrintouts.class);

    return new TubPrintOuts(titles, authors, manuscripts, editions);
  }

  private List<Data> getTubData(final String query) {
    var list = fetchData(query);
    while (offset != STOP) {
      list = Stream.of(list, fetchData(query + "|offset=" + offset)).flatMap(List::stream).toList();
    }
    return list;
  }

  private <T extends Printouts> Map<String, ArrayList<T>> getMapPrintouts(
      String query, Class<T> printoutsClass) {
    final var printouts = getTubData(query);
    return createMap(printouts, printoutsClass);
  }

  private <T extends Printouts> Map<String, ArrayList<T>> createMap(
      List<Data> dataList, Class<T> printoutsClass) {
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
