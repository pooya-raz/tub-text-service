package org.tub.tubtextservice.tubdata.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.builder.AuthorPrintoutsBuilder;
import org.tub.tubtextservice.builder.EditionPrintoutsBuilder;
import org.tub.tubtextservice.builder.ManuscriptPrintoutsBuilder;
import org.tub.tubtextservice.builder.TitlePrintoutsBuilder;
import org.tub.tubtextservice.domain.tubdata.model.TubPrintouts;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.Data;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.Query;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.Results;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.TubResponse;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts.TitlePrintouts;
import org.tub.tubtextservice.domain.tubdata.service.TubApiService;
import org.tub.tubtextservice.infrastructure.client.TubClient;
import org.tub.tubtextservice.infrastructure.property.QueryProperties;
import org.tub.tubtextservice.infrastructure.property.TubProperties;
import reactor.core.publisher.Mono;

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
      AuthorPrintoutsBuilder.builder().fullNameTransliterated(AUTHOR_NAME).build();
  public static final TubResponse AUTHOR_RESPONSE = getTubResponse(AUTHOR_PRINTOUTS, AUTHOR_NAME);
  public static final String TITLE = "Title";
  public static final EditionPrintouts EDITION_PRINTOUTS =
      EditionPrintoutsBuilder.builder()
          .titleTransliterated("Edition")
          .publishedEditionOfTitle(TITLE)
          .build();

  public static final TubResponse EDITION_RESPONSE = getTubResponse(EDITION_PRINTOUTS, TITLE);
  public static final ManuscriptPrintouts MANUSCRIPT_PRINTOUTS =
      ManuscriptPrintoutsBuilder.builder().manuscriptOfTitle(TITLE).manuscriptNumber("1").build();
  public static final TubResponse MANUSCRIPT_RESPONSE = getTubResponse(MANUSCRIPT_PRINTOUTS, TITLE);
  public static final TitlePrintouts TITLE_PRINTOUTS =
      TitlePrintoutsBuilder.builder().titleTransliterated(TITLE).build();
  public static final TubResponse TITLE_RESPONSE = getTubResponse(TITLE_PRINTOUTS, TITLE);
  private TubApiService subject;

  @Mock private TubClient tubClient;

  private static TubResponse getTubResponse(Printouts printouts, String fulltext, int offset) {
    final var data = Data.builder().printouts(printouts).fulltext(fulltext).build();
    final var results = new Results();
    results.setDataMap("1", data);
    final var query = new Query(List.of(), results, null, null, null);
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

    final Map<String, TitlePrintouts> titleMap = Map.of(TITLE, TITLE_PRINTOUTS);
    final Map<String, AuthorPrintouts> authorMap = Map.of(AUTHOR_NAME, AUTHOR_PRINTOUTS);
    final Map<String, ArrayList<ManuscriptPrintouts>> manuscriptMap =
        Map.of(TITLE, new ArrayList<>(List.of(MANUSCRIPT_PRINTOUTS)));
    final Map<String, ArrayList<EditionPrintouts>> editionMap =
        Map.of(TITLE, new ArrayList<>(List.of(EDITION_PRINTOUTS)));
    final var expected = new TubPrintouts(titleMap, authorMap, manuscriptMap, editionMap);

    Assertions.assertThat(subject.getData()).isEqualTo(expected);
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
    Assertions.assertThat(actual.titles()).hasSize(2);
  }
}
