package org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response;

public record MediaWikiPageDetails(
        String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {}
