package org.tub.tubtextservice.service.tubapi.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TubResponse(
    @JsonProperty("query-continue-offset") Integer queryContinueOffset, Query query) {}
