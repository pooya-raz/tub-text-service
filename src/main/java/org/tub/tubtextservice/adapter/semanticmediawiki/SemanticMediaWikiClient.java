package org.tub.tubtextservice.adapter.semanticmediawiki;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.TubResponse;
import reactor.core.publisher.Mono;

@HttpExchange()
public interface SemanticMediaWikiClient {
    @GetExchange()
    Mono<TubResponse> queryTub(
            @RequestParam String action, @RequestParam String format, @RequestParam String query);
}
