package org.tub.tubtextservice.adapter.semantic.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Query(
    @JsonProperty("printrequests") List<PrintRequest> printRequests,
    Results results,
    String serializer,
    Long version,
    Meta meta) {

    public Query {
        printRequests = List.copyOf(printRequests);
    }
}
