package org.tub.tubtextservice.service.tubapi.model.tubresponse;

public record PrintRequest(
    String label, String key, String redi, String typeid, Long mode, String format) {}
