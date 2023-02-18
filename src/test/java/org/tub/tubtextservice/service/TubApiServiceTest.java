package org.tub.tubtextservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.service.tubapi.client.TubClient;
import org.tub.tubtextservice.model.property.QueryProperties;
import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Data;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Query;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.Results;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.TubResponse;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;
import org.tub.tubtextservice.service.tubapi.TubApiService;
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
  public static final AuthorPrintouts AUTHOR_PRINTOUTS = AuthorPrintouts.builder().fullNameTransliterated("Jim").build();
  public static final TubResponse AUTHOR_RESPONSE = getTubResponse(AUTHOR_PRINTOUTS);
  public static final EditionPrintouts EDITION_PRINTOUTS = EditionPrintouts.builder().titleTransliterated("Edition").publishedEditionOfTitle("Title").build();
  public static final TubResponse EDITION_RESPONSE = getTubResponse(EDITION_PRINTOUTS);
  public static final ManuscriptPrintouts MANUSCRIPT_PRINTOUTS =ManuscriptPrintouts.builder().manuscriptOfTitle("Title").manuscriptNumber("1").build();
  public static final TubResponse MANUSCRIPT_RESPONSE = getTubResponse(MANUSCRIPT_PRINTOUTS);
  public static final TitlePrintouts TITLE_PRINTOUTS = TitlePrintouts.builder().titleTransliterated("Title").build();
  public static final TubResponse TITLE_RESPONSE = getTubResponse(TITLE_PRINTOUTS);
  private TubApiService subject;

  @Mock private TubClient tubClient;

  private static TubResponse getTubResponse(Printouts printouts, int offset) {
    final var data = Data.builder().printouts(printouts).build();
    final var results = new Results();
    results.setDataMap("1", data);
    final var query = new Query(null, results, null, null, null);
    return new TubResponse(offset, query);
  }
  private static TubResponse getTubResponse(Printouts printouts) {
    return getTubResponse(printouts, 0);
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
    assertThat(actual.authors().get("Jim")).isEqualTo(List.of(AUTHOR_PRINTOUTS));
    assertThat(actual.titles().get(0)).isEqualTo(TITLE_PRINTOUTS);
    assertThat(actual.editions().get("Title")).isEqualTo(List.of(EDITION_PRINTOUTS));
    assertThat(actual.manuscripts().get("Title")).isEqualTo(List.of(MANUSCRIPT_PRINTOUTS));
  }

  @Test
  void getDataShouldGetGetMoreDataIfThereIsOffset() {
    final var titleResponseWithOffset = getTubResponse(TITLE_PRINTOUTS, 1);
    when(tubClient.queryTub(ASK, JSON, TITLES)).thenReturn(Mono.just(titleResponseWithOffset));
    when(tubClient.queryTub(ASK, JSON, TITLES + "|offset=1")).thenReturn(Mono.just(TITLE_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, AUTHORS)).thenReturn(Mono.just(AUTHOR_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(MANUSCRIPT_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(EDITION_RESPONSE));
    final var actual = subject.getData();
    assertThat(actual.titles().size()).isEqualTo(2);
  }

  @Test
  void getDataShouldNotAddMapIfKeyIsMissing(){
    final var authorResponse = getTubResponse(AuthorPrintouts.builder().build());
    final var manuscriptResponse = getTubResponse(ManuscriptPrintouts.builder().build());
    final var editionResponse = getTubResponse(EditionPrintouts.builder().build());
    when(tubClient.queryTub(ASK, JSON, TITLES)).thenReturn(Mono.just(TITLE_RESPONSE));
    when(tubClient.queryTub(ASK, JSON, AUTHORS)).thenReturn(Mono.just(authorResponse));
    when(tubClient.queryTub(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(manuscriptResponse));
    when(tubClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(editionResponse));
    final var actual = subject.getData();
    assertThat(actual.authors().size()).isEqualTo(0);
    assertThat(actual.manuscripts().size()).isEqualTo(0);
    assertThat(actual.editions().size()).isEqualTo(0);
  }
}
