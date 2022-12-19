package org.tub.tubtextservice.model.tubresponse;

public record Author(
    String fullText,
    String fullUrl,
    Integer namespace,
    String exists,
    String displayTitle) {}
