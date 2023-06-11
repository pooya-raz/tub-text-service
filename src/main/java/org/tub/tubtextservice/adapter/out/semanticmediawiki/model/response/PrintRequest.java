package org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response;

public record PrintRequest(String label, String key, String redi, String typeid, Long mode, String format) {}
