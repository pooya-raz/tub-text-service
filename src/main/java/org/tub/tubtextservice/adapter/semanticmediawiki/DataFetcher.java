package org.tub.tubtextservice.adapter.semanticmediawiki;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.Data;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.TubResponse;

/** Responsible fore retrieving the data from the TUB API. */
@Component
public class DataFetcher {

  /** The stop value is used to stop the retrieval of data from the TUB API. */
  public static final int STOP = 0;

  private final SemanticMediaWikiClient semanticMediaWikiClient;
  /**
   * The offset is used to retrieve the data in batches. The offset is the number of items to skip.
   */
  private int offset = STOP;

  public DataFetcher(SemanticMediaWikiClient semanticMediaWikiClient) {
    this.semanticMediaWikiClient = semanticMediaWikiClient;
  }

  /**
   * Concatenates the data from the TUB API that is returned in multiple requests.
   *
   * @param query the query as defined by Semantic MediaWiki.
   * @return the concatenated {@link Data} from the TUB API.
   */
  public List<Data> getAllData(final String query) {
    var list = fetchData(query);
    while (offset != STOP) {
      list = Stream.of(list, fetchData(query + "|offset=" + offset)).flatMap(List::stream).toList();
    }
    return list;
  }

  /**
   * Fetches the data from TUB API. The data is batched based on the value of the {@link
   * DataFetcher#offset}. The offset is initially set to 0 and is only updated if the response
   * contains a value for {@link TubResponse#queryContinueOffset()}.
   *
   * @param query the query as defined by Semantic MediaWiki.
   * @return the {@link Data} from the TUB API.
   */
  private List<Data> fetchData(final String query) {

    if (query == null) return List.of();
    final var result = semanticMediaWikiClient.queryTub("ask", "json", query).blockOptional();
    return result
        .map(
            r -> {
              if (result.get().queryContinueOffset() != null) {
                offset = result.get().queryContinueOffset();
              } else {
                offset = STOP;
              }
              return r.query().results().getDataMap().values().stream().toList();
            })
        .orElseGet(
            () -> {
              offset = STOP;
              return List.of();
            });
  }
}
