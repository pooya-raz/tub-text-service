package org.tub.tubtextservice.service;

import org.tub.tubtextservice.client.TubClient;
import org.tub.tubtextservice.model.TubData;
import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.model.tubresponse.Data;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TubApiService {
  private static final String ACTION_ASK = "ask";
  private static final String FORMAT_JSON = "json";
  private final TubClient tubClient;
  private final TubProperties tub;
  private int offset = 0;

  public TubApiService(TubClient tubClient, TubProperties tub) {
    this.tubClient = tubClient;
    this.tub = tub;
  }

  public TubData getData() {
    final var titles = getPrintouts(tub.query().titles(), TitlePrintouts.class);
    final var authors = getPrintouts(tub.query().authors(), AuthorPrintouts.class);
    final var manuscripts = getPrintouts(tub.query().manuscripts(), ManuscriptPrintouts.class);
    final var editions = getPrintouts(tub.query().editions(), EditionPrintouts.class);

    return new TubData(titles, authors, manuscripts, editions);
  }

  private <T extends Printouts> List<T> getPrintouts(
      final String query, final Class<T> printoutClass) {
    var list = fetchData(query);
    while (offset != 0) {
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
              offset = 0;
              return List.of();
            });
  }
}
