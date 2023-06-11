package org.tub.tubtextservice.common;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import java.util.Map;

public class WireMockTestConstants {
    public static final Map<String, StringValuePattern> SEMANTIC_SEARCH_PARAMS =
            Map.of("action", equalTo("ask"), "format", equalTo("json"));

    private WireMockTestConstants() {
        throw new IllegalStateException("Utility class");
    }
}
