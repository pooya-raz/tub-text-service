package org.tub.tubtextservice.model.tubresponse;

public record PrintRequest(
    String label, String key, String redi, TypeId typeid, Long mode, String format) {}
