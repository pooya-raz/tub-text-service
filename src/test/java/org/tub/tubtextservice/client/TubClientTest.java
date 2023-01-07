package org.tub.tubtextservice.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.tub.tubtextservice.helper.TubResponseHelper;
import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TubClientTest {
  private static WireMockServer server;
  @Autowired private TubClient subject;

  @BeforeAll
  static void setUpBeforeAll() {
    server = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    server.start();
    configureFor(server.port());
  }

  @AfterAll
  static void tearDownAfterAll() {
    server.shutdown();
  }

  @DynamicPropertySource
  static void properties(final DynamicPropertyRegistry registry) {
    registry.add("tub.api-url", server::baseUrl);
  }

  @BeforeEach
  void setUpBeforeEach() {
    server.resetAll();
  }

  @Test
  void queryTubShouldReturnWithValidJson() throws IOException {
    final var response =
        Files.readString(Paths.get("src/test/resources/tub/semantic-query/title.json"));
    final var expectedResponse = TubResponseHelper.createTubResponse();

    server.stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParam("action", equalTo("ask"))
            .withQueryParam("format", equalTo("json"))
            .withQueryParam("query", equalTo("query"))
            .willReturn(okJson(response)));
    StepVerifier.create(subject.queryTub("ask", "json", "query"))
        .assertNext(
            actual -> {
              assertEquals(expectedResponse.queryContinueOffset(), actual.queryContinueOffset());
              assertEquals(
                  expectedResponse.query().results().getDataMap().entrySet().stream()
                      .findFirst()
                      .map(json -> json.getValue().printouts())
                      .orElse(null),
                  actual.query().results().getDataMap().entrySet().stream()
                      .findFirst()
                      .map(json -> json.getValue().printouts())
                      .orElse(null));
            })
        .verifyComplete();
  }

  @Test
  void queryTubShouldReturnWithAuthorWhenQueriedForAuthor() throws IOException {
    final var response =
        Files.readString(Paths.get("src/test/resources/tub/semantic-query/author.json"));
    final var expectedResponse =
        new AuthorPrintouts(
            List.of("ʿAbbās b. Ḥasan Kāshif al-Ghiṭāʾ"),
            List.of(1323),
            List.of(new MediaWikiDate(-2051222400L, "1/1905")),
            List.of(),
            List.of(),
            List.of(),
            List.of());

    server.stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParam("action", equalTo("ask"))
            .withQueryParam("format", equalTo("json"))
            .withQueryParam("query", equalTo("author"))
            .willReturn(okJson(response)));
    StepVerifier.create(subject.queryTub("ask", "json", "author"))
        .assertNext(
            actual ->
                assertEquals(
                    expectedResponse,
                    actual.query().results().getDataMap().entrySet().stream()
                        .findFirst()
                        .map(json -> json.getValue().printouts())
                        .orElse(null)))
        .verifyComplete();
  }

  @Test
  void queryTubShouldReturnWithAuthorWhenQueriedForMultipleAuthors() throws IOException {
    final var response =
        Files.readString(Paths.get("src/test/resources/tub/semantic-query/authors.json"));
    final var expectedResponse =
        new AuthorPrintouts(
            List.of("ʿAbbās b. Ḥasan Kāshif al-Ghiṭāʾ"),
            List.of(1323),
            List.of(new MediaWikiDate(-2051222400L, "1/1905")),
            List.of(),
            List.of(),
            List.of(),
            List.of());

    server.stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParam("action", equalTo("ask"))
            .withQueryParam("format", equalTo("json"))
            .withQueryParam("query", equalTo("author"))
            .willReturn(okJson(response)));
    StepVerifier.create(subject.queryTub("ask", "json", "author"))
        .assertNext(
            actual ->
                assertEquals(
                    expectedResponse,
                    actual.query().results().getDataMap().entrySet().stream()
                        .findFirst()
                        .map(json -> json.getValue().printouts())
                        .orElse(null)))
        .verifyComplete();
  }
}
