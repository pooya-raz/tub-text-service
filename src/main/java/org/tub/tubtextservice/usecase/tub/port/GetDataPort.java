package org.tub.tubtextservice.usecase.tub.port;

import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.TubResponse;
import reactor.core.publisher.Mono;

public interface GetDataPort {
    Mono<TubResponse> queryTub(
             String action, String format,  String query);
}
