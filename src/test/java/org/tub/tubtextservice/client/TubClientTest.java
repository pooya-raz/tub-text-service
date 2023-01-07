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
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;
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
  void queryTubShouldReturnWithManuscriptWhenQueriedForManuscript() throws IOException {
    final var response =
        Files.readString(Paths.get("src/test/resources/tub/semantic-query/manuscript.json"));
    final var city =
        new MediaWikiPageDetails(
            "Tehran", "http://10.164.39.147:8080/tub/index.php/Tehran", 0, "1", "");
    final var title =
        new MediaWikiPageDetails(
            "Abwāb al-jinān al-mushtamil ʿalā rasāʾil thamān",
            "http://10.164.39.147:8080/tub/index.php/Abw%C4%81b_al-jin%C4%81n_al-mushtamil_%CA%BFal%C4%81_ras%C4%81%CA%BEil_tham%C4%81n",
            0,
            "1",
            "");
    final var expectedResponse =
        new ManuscriptPrintouts(
            List.of("Majlis"),
            List.of("Fankha 1:371"),
            List.of(1699),
            List.of("17th century"),
            List.of(1099),
            List.of("11th century"),
            List.of(),
            List.of(),
            List.of(city),
            List.of("1195"),
            List.of(title));

    server.stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParam("action", equalTo("ask"))
            .withQueryParam("format", equalTo("json"))
            .withQueryParam("query", equalTo("manuscript"))
            .willReturn(okJson(response)));
    StepVerifier.create(subject.queryTub("ask", "json", "manuscript"))
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
  void queryTubShouldCorrectlyParseEditionPrintouts() throws IOException {
    final var response =
        Files.readString(Paths.get("src/test/resources/tub/semantic-query/edition.json"));
    final var city =
        new MediaWikiPageDetails(
            "Tehran", "http://10.164.39.147:8080/tub/index.php/Tehran", 0, "1", "");
    final var title =
        new MediaWikiPageDetails(
            "Anīs al-mujtahidīn",
            "http://10.164.39.147:8080/tub/index.php/An%C4%ABs_al-mujtahid%C4%ABn",
            0,
            "1",
            "");
    final var expectedResponse =
        new EditionPrintouts(
            List.of("Anīs al-mujtahidīn"),
            List.of("أنيس المجتهدين"),
            List.of(2009),
            List.of("2009-10"),
            List.of(1430),
            List.of("1430-31"),
            List.of(1388),
            List.of("1388"),
            List.of(title),
            List.of(),
            List.of("Bustān-i Kitāb"),
            List.of(),
            List.of(),
            List.of(city));

    server.stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParam("action", equalTo("ask"))
            .withQueryParam("format", equalTo("json"))
            .withQueryParam("query", equalTo("manuscript"))
            .willReturn(okJson(response)));
    StepVerifier.create(subject.queryTub("ask", "json", "manuscript"))
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
