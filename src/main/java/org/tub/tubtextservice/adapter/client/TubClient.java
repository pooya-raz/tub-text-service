package org.tub.tubtextservice.adapter.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.TubResponse;
import org.tub.tubtextservice.usecase.tub.port.GetDataPort;
import reactor.core.publisher.Mono;

@HttpExchange()
public interface TubClient extends GetDataPort {
    @GetExchange()
    Mono<TubResponse> queryTub(
            @RequestParam String action, @RequestParam String format, @RequestParam String query);
}
