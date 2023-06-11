package org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TubResponse(@JsonProperty("query-continue-offset") Integer queryContinueOffset, Query query) {}
