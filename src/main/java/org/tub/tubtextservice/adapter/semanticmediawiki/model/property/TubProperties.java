package org.tub.tubtextservice.adapter.semanticmediawiki.model.property;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tub")
public record TubProperties(
    String apiUrl, Integer retryMaxAttempts, Duration retryBackoffPeriod, QueryProperties query) {}
