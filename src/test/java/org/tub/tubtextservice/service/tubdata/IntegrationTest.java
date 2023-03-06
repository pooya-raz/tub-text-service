package org.tub.tubtextservice.service.tubdata;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 9090)
public class IntegrationTest {

  public static final String TITLES = "titles";
  public static final String AUTHORS = "authors";
  public static final String EDITIONS = "editions";
  public static final String MANUSCRIPTS = "manuscripts";

  public static final String TUB_API_ENDPOINT = "/tub/api.php";
  public static final Map<String, StringValuePattern> SEMANTIC_SEARCH_PARAMS =
      Map.of("action", equalTo("ask"), "format", equalTo("json"));
  public static final String QUERY = "query";
  @Autowired private TubDataService tubDataService;

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("tub.api-url", () -> "http://localhost:9090" + TUB_API_ENDPOINT);
    registry.add("tub.query.titles", () -> TITLES);
    registry.add("tub.query.authors", () -> AUTHORS);
    registry.add("tub.query.editions", () -> EDITIONS);
    registry.add("tub.query.manuscripts", () -> MANUSCRIPTS);
  }

  @Test
  void shouldGetEntries() throws IOException {
    final var authorJson =
        Files.readString(Path.of("src/test/resources/tub/semantic-query/author.json"));
    final var titleJson =
        Files.readString(Path.of("src/test/resources/tub/semantic-query/title.json"));
    final var editionJson =
        Files.readString(Path.of("src/test/resources/tub/semantic-query/edition.json"));
    final var manuscriptJson =
        Files.readString(Path.of("src/test/resources/tub/semantic-query/manuscript.json"));

    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(TITLES))
            .willReturn(okJson(titleJson)));

    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(AUTHORS))
            .willReturn(okJson(authorJson)));

    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(EDITIONS))
            .willReturn(okJson(editionJson)));

    stubFor(
        get(urlPathEqualTo(TUB_API_ENDPOINT))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam(QUERY, equalTo(MANUSCRIPTS))
            .willReturn(okJson(manuscriptJson)));

    final var entries = tubDataService.getEntries();

    assertThat(entries).isNotEmpty();
  }
}
