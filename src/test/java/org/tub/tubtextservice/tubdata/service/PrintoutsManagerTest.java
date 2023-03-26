package org.tub.tubtextservice.tubdata.service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrintoutsManagerTest {
/*
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
  private PrintoutsManager subject;

  @Mock private DataFetcher dataFetcher;

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
    subject = new PrintoutsManager( props);
  }

  @Test
  void getDataShouldGetDataForTubCategories() {
    when(dataFetcher.getAllData(TITLES)).thenReturn(Mono.just(TITLE_RESPONSE));
    when(dataFetcher.getAllData(ASK, JSON, AUTHORS)).thenReturn(Mono.just(AUTHOR_RESPONSE));
    when(dataFetcher.getAllData(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(MANUSCRIPT_RESPONSE));
    when(semanticMediaWikiClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(EDITION_RESPONSE));

    final Map<String, TitlePrintouts> titleMap = Map.of(TITLE, TITLE_PRINTOUTS);
    final Map<String, AuthorPrintouts> authorMap = Map.of(AUTHOR_NAME, AUTHOR_PRINTOUTS);
    final Map<String, ArrayList<ManuscriptPrintouts>> manuscriptMap =
        Map.of(TITLE, new ArrayList<>(List.of(MANUSCRIPT_PRINTOUTS)));
    final Map<String, ArrayList<EditionPrintouts>> editionMap =
        Map.of(TITLE, new ArrayList<>(List.of(EDITION_PRINTOUTS)));
    final var expected = new TubPrintouts(titleMap, authorMap, manuscriptMap, editionMap);

    Assertions.assertThat(subject.getPrintouts()).isEqualTo(expected);
  }

  @Test
  void getDataShouldGetGetMoreDataIfThereIsOffset() {
    final var titleResponseWithOffset = getTubResponse(TITLE_PRINTOUTS, TITLE + "1", 1);
    when(semanticMediaWikiClient.queryTub(ASK, JSON, TITLES)).thenReturn(Mono.just(titleResponseWithOffset));
    when(semanticMediaWikiClient.queryTub(ASK, JSON, TITLES + "|offset=1")).thenReturn(Mono.just(TITLE_RESPONSE));
    when(semanticMediaWikiClient.queryTub(ASK, JSON, AUTHORS)).thenReturn(Mono.just(AUTHOR_RESPONSE));
    when(semanticMediaWikiClient.queryTub(ASK, JSON, MANUSCRIPTS)).thenReturn(Mono.just(MANUSCRIPT_RESPONSE));
    when(semanticMediaWikiClient.queryTub(ASK, JSON, EDITIONS)).thenReturn(Mono.just(EDITION_RESPONSE));
    final var actual = subject.getPrintouts();
    Assertions.assertThat(actual.titles()).hasSize(2);
  }
  
 */
}
