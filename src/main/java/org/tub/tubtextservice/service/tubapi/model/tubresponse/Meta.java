package org.tub.tubtextservice.service.tubapi.model.tubresponse;

public record Meta(
        String hash,
        long count,
        long offset,
        String source,
        String time) {}
