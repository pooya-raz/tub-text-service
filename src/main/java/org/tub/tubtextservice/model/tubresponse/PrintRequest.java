package org.tub.tubtextservice.model.tubresponse;

public record PrintRequest(
    String label, String key, String redi, String typeid, Long mode, String format) {}
