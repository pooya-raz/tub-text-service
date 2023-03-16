package org.tub.tubtextservice.client;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static java.nio.file.Files.readString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tub.tubtextservice.WireMockTestConstants.SEMANTIC_SEARCH_PARAMS;
import static reactor.test.StepVerifier.create;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.tub.tubtextservice.helper.TubResponseHelper;
import org.tub.tubtextservice.service.tubdata.client.TubClient;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiDate;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.ManuscriptPrintouts;

@SpringBootTest
@WireMockTest
class TubClientTest {
  private static int PORT;
  @Autowired private TubClient subject;

  @BeforeAll
  static void setUpBeforeAll(WireMockRuntimeInfo wireMockRuntimeInfo) {
    PORT = wireMockRuntimeInfo.getHttpPort();
  }

  @DynamicPropertySource
  static void properties(final DynamicPropertyRegistry registry) {
    registry.add("tub.api-url", () -> "http://localhost:" + PORT);
  }

  @Test
  void queryTubShouldReturnWithValidJson() throws IOException {
    final var response = readString(Paths.get("src/test/resources/tub/semantic-query/title.json"));
    final var expectedResponse = TubResponseHelper.createTubResponse();

    stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam("query", equalTo("query"))
            .willReturn(okJson(response)));

    create(subject.queryTub("ask", "json", "query"))
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
    final var response = readString(Paths.get("src/test/resources/tub/semantic-query/author.json"));
    final var expectedResponse =
        new AuthorPrintouts(
            List.of("Author"),
            List.of(1323),
            List.of(new MediaWikiDate(-2051222400L, "1/1905")),
            List.of(),
            List.of(),
            List.of(),
            List.of());

    stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam("query", equalTo("author"))
            .willReturn(okJson(response)));

    create(subject.queryTub("ask", "json", "author"))
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
        readString(Paths.get("src/test/resources/tub/semantic-query/manuscript.json"));
    final var city =
        new MediaWikiPageDetails(
            "Tehran", "http://10.164.39.147:8080/tub/index.php/Tehran", 0, "1", "");
    final var title =
        new MediaWikiPageDetails(
            "Title Transliterated",
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

    stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam("query", equalTo("manuscript"))
            .willReturn(okJson(response)));

    create(subject.queryTub("ask", "json", "manuscript"))
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
        readString(Paths.get("src/test/resources/tub/semantic-query/edition.json"));
    final var city =
        new MediaWikiPageDetails(
            "Tehran", "http://10.164.39.147:8080/tub/index.php/Tehran", 0, "1", "");
    final var title =
        new MediaWikiPageDetails(
            "Title Transliterated",
            "http://10.164.39.147:8080/tub/index.php/An%C4%ABs_al-mujtahid%C4%ABn",
            0,
            "1",
            "");
    final var expectedResponse =
        new EditionPrintouts(
            List.of("Title Transliterated"),
            List.of("Title Arabic"),
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

    stubFor(
        get(urlPathEqualTo("/"))
            .withQueryParams(SEMANTIC_SEARCH_PARAMS)
            .withQueryParam("query", equalTo("manuscript"))
            .willReturn(okJson(response)));
    create(subject.queryTub("ask", "json", "manuscript"))
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
