package org.tub.tubtextservice.service.tubdata;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static java.nio.file.Files.readString;
import static java.nio.file.Path.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.tub.tubtextservice.WireMockTestConstants.PORT;
import static org.tub.tubtextservice.WireMockTestConstants.SEMANTIC_SEARCH_PARAMS;
import static org.tub.tubtextservice.WireMockTestConstants.URL;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = PORT)
public class IntegrationTest {

  public static final String TITLES = "titles";
  public static final String AUTHORS = "authors";
  public static final String EDITIONS = "editions";
  public static final String MANUSCRIPTS = "manuscripts";

  public static final String TUB_API_ENDPOINT = "/tub/api.php";

  public static final String QUERY = "query";
  public static final String TITLES_JSON = "src/test/resources/tub/semantic-query/title.json";
  public static final String AUTHORS_JSON = "src/test/resources/tub/semantic-query/author.json";
  public static final String EDITIONS_JSON = "src/test/resources/tub/semantic-query/edition.json";
  public static final String MANUSCRIPTS_JSON =
      "src/test/resources/tub/semantic-query/manuscript.json";
  public static final String RETRY = "Retry";
  public static final String SUCCESS = "Success";
  public static final String RETRY_1 = "Retry 1";
  @Autowired private TubDataService tubDataService;

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("tub.api-url", () -> URL + TUB_API_ENDPOINT);
    registry.add("tub.query.titles", () -> TITLES);
    registry.add("tub.query.authors", () -> AUTHORS);
    registry.add("tub.query.editions", () -> EDITIONS);
    registry.add("tub.query.manuscripts", () -> MANUSCRIPTS);
  }

  @Test
  void shouldGetEntries() throws IOException {
    stubForTUB(TITLES, TITLES_JSON);
    stubForTUB(AUTHORS, AUTHORS_JSON);
    stubForTUB(EDITIONS, EDITIONS_JSON);
    stubForTUB(MANUSCRIPTS, MANUSCRIPTS_JSON);

    final var actual = tubDataService.getEntries();
    assertThat(actual).hasSize(1);
  }

  @Test
  void shouldRetryThreeTimes() throws IOException {
    stubForRetry(TITLES, TITLES_JSON);
    stubForTUB(AUTHORS, AUTHORS_JSON);
    stubForTUB(EDITIONS, EDITIONS_JSON);
    stubForTUB(MANUSCRIPTS, MANUSCRIPTS_JSON);

    final var actual = tubDataService.getEntries();
    assertThat(actual).hasSize(1);
  }

  private void stubForTUB(String query, String path) throws IOException {
    final var jsonString = readString(of(path));
    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(query))
            .willReturn(okJson(jsonString)));
  }

  private void stubForRetry(String query, String path) throws IOException {
    final var jsonString = readString(of(path));
    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .inScenario(RETRY)
            .whenScenarioStateIs(STARTED)
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(query))
            .willReturn(status(500))
            .willSetStateTo(RETRY_1));
    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .inScenario(RETRY)
            .whenScenarioStateIs(RETRY_1)
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(query))
            .willReturn(status(500))
            .willSetStateTo(SUCCESS));
    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .inScenario(RETRY)
            .whenScenarioStateIs(SUCCESS)
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(query))
            .willReturn(okJson(jsonString)));
  }
}
