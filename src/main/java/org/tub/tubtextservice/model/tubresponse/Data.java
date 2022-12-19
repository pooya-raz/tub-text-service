package org.tub.tubtextservice.model.tubresponse;

public record Data(
    Printouts printouts,
    String fullText,
    String fullUrl,
    Integer namespace,
    String exists,
    String displayTitle) {}
