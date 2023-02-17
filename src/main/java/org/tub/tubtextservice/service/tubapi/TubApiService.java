package org.tub.tubtextservice.service.tubapi;

import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.service.tubapi.client.TubClient;
import org.tub.tubtextservice.service.tubapi.model.TubPrintOuts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Data;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
    final var titles = getPrintouts(tub.query().titles(), TitlePrintouts.class);
    final var authors = getMapPrintouts(tub.query().authors(), AuthorPrintouts.class);
    final var manuscripts = getMapPrintouts(tub.query().manuscripts(), ManuscriptPrintouts.class);
    final var editions = getMapPrintouts(tub.query().editions(), EditionPrintouts.class);

    return new TubPrintOuts(titles, authors, manuscripts, editions);
  }

  private <T extends Printouts> Map<String, T> getMapPrintouts(
      String query, Class<T> printoutsClass) {
    final var printouts =
        getPrintouts(query, printoutsClass).stream().map(printoutsClass::cast).toList();
    return createMap(printouts);
  }

  private <T extends Printouts> Map<String, T> createMap(List<T> printouts) {
    final var printoutsMap = new HashMap<String, T>();
    printouts.forEach(
        p -> {
          switch (p) {
            case AuthorPrintouts authorPrintouts -> printoutsMap.put(
                authorPrintouts.fullNameTransliterated().stream()
                    .findFirst()
                    .orElse(UUID.randomUUID().toString()),
                p);
            case EditionPrintouts edition -> printoutsMap.put(
                edition.publishedEditionOfTitle().stream()
                    .findFirst()
                    .map(MediaWikiPageDetails::fulltext)
                    .orElse(UUID.randomUUID().toString()),
                p);
            case ManuscriptPrintouts manuscript -> printoutsMap.put(
                manuscript.manuscriptOfTitle().stream()
                    .findFirst()
                    .map(MediaWikiPageDetails::fulltext)
                    .orElse(UUID.randomUUID().toString()),
                p);
            case TitlePrintouts title -> printoutsMap.put(
                title.titleTransliterated().stream()
                    .findFirst()
                    .orElse(UUID.randomUUID().toString()),
                p);
          }
        });
    return printoutsMap;
  }

  private <T extends Printouts> List<T> getPrintouts(
      final String query, final Class<T> printoutClass) {
    var list = fetchData(query);
    while (offset != STOP) {
      list = Stream.of(list, fetchData(query + "|offset=" + offset)).flatMap(List::stream).toList();
    }
    return list.stream().map(printoutClass::cast).toList();
  }

  private List<Printouts> fetchData(final String query) {
    if (query == null) return List.of();
    final var result = tubClient.queryTub(ACTION_ASK, FORMAT_JSON, query).block();
    return Optional.ofNullable(result)
        .map(
            r -> {
              offset = result.queryContinueOffset();
              return r.query().results().getDataMap().values().stream()
                  .map(Data::printouts)
                  .toList();
            })
        .orElseGet(
            () -> {
              offset = STOP;
              return List.of();
            });
  }
}
