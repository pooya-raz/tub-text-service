package org.tub.tubtextservice.adapter.semanticmediawiki.model.response;

public record MediaWikiPageDetails(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {}
