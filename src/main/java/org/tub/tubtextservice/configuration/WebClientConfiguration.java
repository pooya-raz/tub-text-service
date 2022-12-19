package org.tub.tubtextservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.tub.tubtextservice.client.TodoClient;
import org.tub.tubtextservice.client.TubClient;
import org.tub.tubtextservice.model.property.TubProperties;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
public class WebClientConfiguration {

  private final TubProperties tubProperties;
  Logger log = LoggerFactory.getLogger(WebClientConfiguration.class);

  public WebClientConfiguration(TubProperties tubProperties) {
    this.tubProperties = tubProperties;
  }

  @Bean
  TubClient tubClient(final WebClient.Builder builder) {
    final WebClient webClient =
        builder
            .baseUrl(tubProperties.apiUrl())
            .filter(logRequest("Fetching data from TUB: " + tubProperties.apiUrl()))
            .filter(retryFilter("Retrying to fetch data from TUB", tubProperties))
            .build();
    final HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
    return factory.createClient(TubClient.class);
  }

  @Bean
  TodoClient todoClient() {
    WebClient webClient =
        WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com").build();
    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
    return factory.createClient(TodoClient.class);
  }

  private ExchangeFilterFunction retryFilter(String text, TubProperties properties) {
    return (request, next) ->
        next.exchange(request)
            .flatMap(
                clientResponse ->
                    Mono.just(clientResponse)
                        .filter(response -> clientResponse.statusCode().isError())
                        .flatMap(response -> clientResponse.createException())
                        .flatMap(Mono::error)
                        .thenReturn(clientResponse))
            .retryWhen(
                Retry.backoff(properties.retryMaxAttempts(), properties.retryBackoffPeriod())
                    .doBeforeRetry(retrySignal -> log.warn(text)));
  }

  private ExchangeFilterFunction logRequest(String text) {
    return (clientRequest, nextFilter) -> {
      log.info(text);
      return nextFilter.exchange(clientRequest);
    };
  }
}
