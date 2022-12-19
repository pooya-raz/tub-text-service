package org.tub.tubtextservice.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TubResponse(
    @JsonProperty("query-continue-offset") Integer queryContinueOffset, Query query) {}
