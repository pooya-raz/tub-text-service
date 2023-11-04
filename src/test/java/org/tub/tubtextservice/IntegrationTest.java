package org.tub.tubtextservice;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static java.nio.file.Files.readString;
import static java.nio.file.Path.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.tub.tubtextservice.common.WireMockTestConstants.SEMANTIC_SEARCH_PARAMS;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@WireMockTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    public static final String TITLES = "titles";
    public static final String AUTHORS = "authors";
    public static final String EDITIONS = "editions";
    public static final String MANUSCRIPTS = "manuscripts";
    public static final String TRANSLATORS = "translators";
    public static final String TUB_API_ENDPOINT = "/tub/api.php";
    public static final String QUERY = "query";
    public static final String AUTHOR_JSON = "src/test/resources/tub/response/author-offset-0.json";
    public static final String TRANSLATOR_JSON = "src/test/resources/tub/response/translator-offset-0.json";
    public static final String EDITION_JSON = "src/test/resources/tub/response/edition-offset-0.json";
    public static final String TITLES_JSON = "src/test/resources/tub/response/title-offset-0.json";
    public static final String TITLES_1000_JSON = "src/test/resources/tub/response/title-offset-1000.json";
    public static final String MANUSCRIPT_JSON = "src/test/resources/tub/response/manuscript-offset-0.json";
    public static final String MANUSCRIPT_1000_JSON = "src/test/resources/tub/response/manuscript-offset-1000.json";
    public static final String MANUSCRIPT_2000_JSON = "src/test/resources/tub/response/manuscript-offset-2000.json";
    public static final String MANUSCRIPT_3000_JSON = "src/test/resources/tub/response/manuscript-offset-3000.json";
    private static final String OFFSET = "|offset=";

    public static int WIREMOCK_PORT;

    @Autowired
    private WebTestClient webtestclient;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("tub.api-url", () -> "http://localhost:" + WIREMOCK_PORT + TUB_API_ENDPOINT);
        registry.add("tub.query.titles", () -> TITLES);
        registry.add("tub.query.authors", () -> AUTHORS);
        registry.add("tub.query.translators", () -> TRANSLATORS);
        registry.add("tub.query.editions", () -> EDITIONS);
        registry.add("tub.query.manuscripts", () -> MANUSCRIPTS);
    }

    @BeforeAll
    static void setUpBeforeAll(WireMockRuntimeInfo wireMockRuntimeInfo) {
        WIREMOCK_PORT = wireMockRuntimeInfo.getHttpPort();
    }

    @Test
    void test() throws IOException {
        stubForTUB(TITLES, TITLES_JSON);
        stubForTUB(TITLES + OFFSET + 1000, TITLES_1000_JSON);
        stubForTUB(MANUSCRIPTS, MANUSCRIPT_JSON);
        stubForTUB(MANUSCRIPTS + OFFSET + 1000, MANUSCRIPT_1000_JSON);
        stubForTUB(MANUSCRIPTS + OFFSET + 2000, MANUSCRIPT_2000_JSON);
        stubForTUB(MANUSCRIPTS + OFFSET + 3000, MANUSCRIPT_3000_JSON);
        stubForTUB(AUTHORS, AUTHOR_JSON);
        stubForTUB(EDITIONS, EDITION_JSON);
        stubForTUB(TRANSLATORS, TRANSLATOR_JSON);

        webtestclient.get().uri("/entry").exchange().expectStatus().isOk();
        final var actualMarkdown = readString(of("src/main/resources/output/markdown.md"));
        final var expectedMarkdown = readString(of("src/test/resources/markdown/markdown.md"));
        assertThat(actualMarkdown).isEqualTo(expectedMarkdown);
    }

    private void stubForTUB(String query, String path) throws IOException {
        final var jsonString = readString(of(path));
        stubFor(get(urlPathEqualTo(TUB_API_ENDPOINT))
                .withQueryParams(SEMANTIC_SEARCH_PARAMS)
                .withQueryParam(QUERY, equalTo(query))
                .willReturn(okJson(jsonString)));
    }
}
