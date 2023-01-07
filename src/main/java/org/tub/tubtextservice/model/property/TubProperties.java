package org.tub.tubtextservice.model.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "tub")
public record TubProperties(
    String apiUrl, Integer retryMaxAttempts, Duration retryBackoffPeriod, QueryProperties query) {}
