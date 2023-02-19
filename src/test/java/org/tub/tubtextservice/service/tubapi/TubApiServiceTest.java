package org.tub.tubtextservice.service.tubapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.model.property.QueryProperties;
import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.service.tubapi.client.TubClient;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Data;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Query;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Results;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.TubResponse;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;
import org.tub.tubtextservice.service.tubapi.service.TubApiService;
import reactor.core.publisher.Mono;

import java.util.List;

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
  public static final String AUTHOR_NAME = "Jim";
  public static final AuthorPrintouts AUTHOR_PRINTOUTS =
      AuthorPrintouts.builder().fullNameTransliterated(AUTHOR_NAME).build();
  public static final TubResponse AUTHOR_RESPONSE = getTubResponse(AUTHOR_PRINTOUTS, AUTHOR_NAME);
  public static final String TITLE = "Title";
  public static final EditionPrintouts EDITION_PRINTOUTS =
      EditionPrintouts.builder()
          .titleTransliterated("Edition")
          .publishedEditionOfTitle(TITLE)
          .build();
  public static final TubResponse EDITION_RESPONSE = getTubResponse(EDITION_PRINTOUTS, TITLE);
  public static final ManuscriptPrintouts MANUSCRIPT_PRINTOUTS =
      ManuscriptPrintouts.builder().manuscriptOfTitle(TITLE).manuscriptNumber("1").build();
  public static final TubResponse MANUSCRIPT_RESPONSE = getTubResponse(MANUSCRIPT_PRINTOUTS, TITLE);
  public static final TitlePrintouts TITLE_PRINTOUTS =
      TitlePrintouts.builder().titleTransliterated(TITLE).build();
  public static final TubResponse TITLE_RESPONSE = getTubResponse(TITLE_PRINTOUTS, TITLE);
  private TubApiService subject;

  @Mock private TubClient tubClient;

  private static TubResponse getTubResponse(Printouts printouts, String fulltext, int offset) {
    final var data = Data.builder().printouts(printouts).fullText(fulltext).build();
    final var results = new Results();
    results.setDataMap("1", data);
    final var query = new Query(null, results, null, null, null);
    return new TubResponse(offset, query);
  }

  private static TubResponse getTubResponse(Printouts printouts, String fulltext) {
    return getTubResponse(printouts, fulltext, 0);
  }

  @BeforeEach
  void setUpBeforeEach() {
    final var queries = new QueryProperties(TITLES, AUTHORS, MANUSCRIPTS, EDITIONS);
    final var props = new TubProperties(null, null, null, queries);
    subject = new TubApiService(tubClient, props);
  }

  @Test
  void getDataShouldGetDataForTubCategories() {
    when(tubClient.queryTub(ASK, JSON, TITLES)).thenReturn(Mono.just(TITLE_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, AUTHORS)).thenReturn(Mono.just(AUTHOR_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(MANUSCRIPT_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(EDITION_RESPONSE));
    final var actual = subject.getData();
    assertThat(actual.authors().get(AUTHOR_NAME)).isEqualTo(List.of(AUTHOR_PRINTOUTS));
    assertThat(actual.titles().get(TITLE)).isEqualTo(List.of(TITLE_PRINTOUTS));
    assertThat(actual.editions().get(TITLE)).isEqualTo(List.of(EDITION_PRINTOUTS));
    assertThat(actual.manuscripts().get(TITLE)).isEqualTo(List.of(MANUSCRIPT_PRINTOUTS));
  }

  @Test
  void getDataShouldGetGetMoreDataIfThereIsOffset() {
    final var titleResponseWithOffset = getTubResponse(TITLE_PRINTOUTS, TITLE + "1", 1);
    when(tubClient.queryTub(ASK, JSON, TITLES)).thenReturn(Mono.just(titleResponseWithOffset));
    when(tubClient.queryTub(ASK, JSON, TITLES + "|offset=1")).thenReturn(Mono.just(TITLE_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, AUTHORS)).thenReturn(Mono.just(AUTHOR_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(MANUSCRIPT_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(EDITION_RESPONSE));
    final var actual = subject.getData();
    assertThat(actual.titles().size()).isEqualTo(2);
  }
}
