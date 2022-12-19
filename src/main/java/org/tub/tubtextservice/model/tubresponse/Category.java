package org.tub.tubtextservice.model.tubresponse;

public record Category(
    String fullText,
    String fullUrl,
    Integer namespace,
    String exists,
    String displayTitle) {}
