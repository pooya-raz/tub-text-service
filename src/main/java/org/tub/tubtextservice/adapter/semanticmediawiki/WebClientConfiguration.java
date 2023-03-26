package org.tub.tubtextservice.adapter.semanticmediawiki;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.property.TubProperties;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

@Configuration
public class WebClientConfiguration {
  private final TubProperties tubProperties;
  Logger log = LoggerFactory.getLogger(WebClientConfiguration.class);

  public WebClientConfiguration(TubProperties tubProperties) {
    this.tubProperties = tubProperties;
  }

  @Bean
  SemanticMediaWikiClient tubClient(final WebClient.Builder builder) {
    final int size = 20 * 1024 * 1024; // 12 MB
    final var client = HttpClient.create().responseTimeout(Duration.ofMinutes(2));
    final var webClient =
        builder
            .baseUrl(tubProperties.apiUrl())
            .filter(logRequest())
            .filter(retryFilter(tubProperties))
            .clientConnector(new ReactorClientHttpConnector(client))
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
    final var factory =
        HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
    return factory.createClient(SemanticMediaWikiClient.class);
  }

  private ExchangeFilterFunction retryFilter(TubProperties properties) {
    return (request, next) ->
        next.exchange(request)
            .flatMap(
                clientResponse -> {
                  log.info("Response from TUB: {}", clientResponse.statusCode());
                  return Mono.just(clientResponse)
                      .filter(response -> clientResponse.statusCode().isError())
                      .flatMap(response -> clientResponse.createException())
                      .flatMap(Mono::error)
                      .thenReturn(clientResponse);
                })
            .retryWhen(
                Retry.backoff(properties.retryMaxAttempts(), properties.retryBackoffPeriod())
                    .doBeforeRetry(retrySignal -> log.warn("Retrying to fetch data from TUB")));
  }

  private ExchangeFilterFunction logRequest() {
    return (clientRequest, nextFilter) -> {
      log.info(
          "Fetching data from TUB: {}, {}",
          keyValue("method", clientRequest.method()),
          keyValue("url", clientRequest.url()));
      return nextFilter.exchange(clientRequest);
    };
  }
}
