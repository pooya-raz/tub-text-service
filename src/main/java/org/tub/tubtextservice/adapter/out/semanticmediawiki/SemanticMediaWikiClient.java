package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.TubResponse;

@HttpExchange()
interface SemanticMediaWikiClient {
    @GetExchange()
    TubResponse queryTub(
            @RequestParam String action, @RequestParam String format, @RequestParam String query);
}
