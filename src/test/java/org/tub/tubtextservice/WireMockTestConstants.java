package org.tub.tubtextservice;

import com.github.tomakehurst.wiremock.matching.StringValuePattern;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

public class WireMockTestConstants {
  public static final int PORT = 23512;
  public static final String URL = "http://localhost" + ":" + PORT;
  public static final Map<String, StringValuePattern> SEMANTIC_SEARCH_PARAMS =
      Map.of("action", equalTo("ask"), "format", equalTo("json"));

  private WireMockTestConstants() {
    throw new IllegalStateException("Utility class");
  }
}
