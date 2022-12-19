package org.tub.tubtextservice.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.tub.tubtextservice.model.tubresponse.TubResponse;
import reactor.core.publisher.Mono;

@HttpExchange()
public interface TubClient {
  @GetExchange()
  Mono<TubResponse> queryTub(
      @RequestParam String action, @RequestParam String format, @RequestParam String query);
}
