package org.tub.tubtextservice.model.tubresponse;

public record Meta(
        String hash,
        long count,
        long offset,
        String source,
        String time) {}
