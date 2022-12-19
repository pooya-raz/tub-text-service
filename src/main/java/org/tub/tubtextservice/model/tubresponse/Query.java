package org.tub.tubtextservice.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Query(
    @JsonProperty("printrequests") List<PrintRequest> printRequests,
    Results results,
    String serializer,
    Long version,
    Meta meta) {}
