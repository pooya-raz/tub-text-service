package org.tub.tubtextservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.client.TubClient;
import org.tub.tubtextservice.model.TubData;
import org.tub.tubtextservice.model.property.QueryProperties;
import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.model.tubresponse.Data;
import org.tub.tubtextservice.model.tubresponse.Query;
import org.tub.tubtextservice.model.tubresponse.Results;
import org.tub.tubtextservice.model.tubresponse.TubResponse;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TubApiServiceTest {

  public static final String ASK = "ask";
  public static final String JSON = "json";
  public static final String TITLES = "titles";
  public static final String AUTHORS = "authors";
  public static final String MANUSCRIPTS = "manuscripts";
  public static final String EDITIONS = "editions";
  public static final AuthorPrintouts AUTHOR_PRINTOUTS =
      new AuthorPrintouts(List.of("Jim"), null, null, null, null, null, null);
  public static final Map<String, AuthorPrintouts> AUTHOR_MAP =
      createMap(AUTHOR_PRINTOUTS, AuthorPrintouts.class);
  public static final EditionPrintouts EDITION_PRINTOUTS =
      new EditionPrintouts(
          List.of("Edition"),
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null);
  public static final Map<String, EditionPrintouts> EDITION_MAP =
      createMap(EDITION_PRINTOUTS, EditionPrintouts.class);
  public static final ManuscriptPrintouts MANUSCRIPT_PRINTOUTS =
      new ManuscriptPrintouts(
          null, null, null, null, null, null, null, null, null, List.of("1"), null);
  public static final Map<String, ManuscriptPrintouts> MANUSCRIPT_MAP =
      createMap(MANUSCRIPT_PRINTOUTS, ManuscriptPrintouts.class);
  public static final TitlePrintouts TITLE_PRINTOUTS =
      new TitlePrintouts(null, null, null, null, List.of("title"), null, null, null, null);
  private TubApiService subject;

  @Mock private TubClient tubClient;

  private static TubResponse getTubResponse(Data data, int offset) {
    final var results = new Results();
    results.setDataMap("1", data);
    final var query = new Query(null, results, null, null, null);
    return new TubResponse(offset, query);
  }

  private static TubResponse getResponse(Printouts printouts, int offset) {
    final var data = new Data(printouts, null, null, null, null, null);
    return getTubResponse(data, offset);
  }

  private static TubResponse getResponse(Printouts printouts) {
    final var data = new Data(printouts, null, null, null, null, null);
    return getTubResponse(data, 0);
  }

  private static <T extends Printouts> Map<String, T> createMap(
      T printouts, Class<T> printoutsClass) {
    final var printoutsMap = new HashMap<String, T>();
    if (printoutsClass.isInstance(AuthorPrintouts.class)) {
      assert printouts instanceof AuthorPrintouts;
      final var authorPrintouts = (AuthorPrintouts) printouts;
      printoutsMap.put(
          authorPrintouts.fullNameTransliterated().get(0), printoutsClass.cast(printouts));
    }
    if (printoutsClass.isInstance(EditionPrintouts.class)) {
      assert printouts instanceof EditionPrintouts;
      final var edition = (EditionPrintouts) printouts;
      printoutsMap.put(
          edition.publishedEditionOfTitle().get(0).fulltext(), printoutsClass.cast(printouts));
    }
    if (printoutsClass.isInstance(ManuscriptPrintouts.class)) {
      assert printouts instanceof ManuscriptPrintouts;
      final var manuscript = (ManuscriptPrintouts) printouts;
      printoutsMap.put(
          manuscript.manuscriptOfTitle().get(0).fulltext(), printoutsClass.cast(printouts));
    }
    return printoutsMap;
  }

  @BeforeEach
  void setUpBeforeEach() {
    final var queries = new QueryProperties(TITLES, AUTHORS, MANUSCRIPTS, EDITIONS);
    final var props = new TubProperties(null, null, null, queries);
    subject = new TubApiService(tubClient, props);
  }

  @Test
  void getDataShouldGetDataForTubCategories() {

    final var expected =
        new TubData(List.of(TITLE_PRINTOUTS), AUTHOR_MAP, MANUSCRIPT_MAP, EDITION_MAP);
    final var titleResponse = getResponse(TITLE_PRINTOUTS);
    final var authorResponse = getResponse(AUTHOR_PRINTOUTS);
    final var manuscriptResponse = getResponse(MANUSCRIPT_PRINTOUTS);
    final var editionResponse = getResponse(EDITION_PRINTOUTS);
    when(tubClient.queryTub(ASK, JSON, TITLES)).thenReturn(Mono.just(titleResponse));
    when(tubClient.queryTub(ASK, JSON, AUTHORS)).thenReturn(Mono.just(authorResponse));
    when(tubClient.queryTub(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(manuscriptResponse));
    when(tubClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(editionResponse));
    final var actual = subject.getData();
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getDataShouldGetGetMoreDataIfThereIsOffset() {
    final var expected =
        new TubData(
            List.of(TITLE_PRINTOUTS, TITLE_PRINTOUTS), AUTHOR_MAP, MANUSCRIPT_MAP, EDITION_MAP);
    final var titleResponseWithOffset = getResponse(TITLE_PRINTOUTS, 1);
    final var titleResponse = getResponse(TITLE_PRINTOUTS, 0);
    final var authorResponse = getResponse(AUTHOR_PRINTOUTS);
    final var manuscriptResponse = getResponse(MANUSCRIPT_PRINTOUTS);
    final var editionResponse = getResponse(EDITION_PRINTOUTS);
    when(tubClient.queryTub(ASK, JSON, TITLES)).thenReturn(Mono.just(titleResponseWithOffset));
    when(tubClient.queryTub(ASK, JSON, TITLES + "|offset=1")).thenReturn(Mono.just(titleResponse));
    when(tubClient.queryTub(ASK, JSON, AUTHORS)).thenReturn(Mono.just(authorResponse));
    when(tubClient.queryTub(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(manuscriptResponse));
    when(tubClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(editionResponse));
    final var actual = subject.getData();
    assertThat(actual.titles().size()).isEqualTo(expected.titles().size());
  }
}
