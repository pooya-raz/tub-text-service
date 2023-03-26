package org.tub.tubtextservice.adapter.semantic.model.tubresponse;

public record MediaWikiPageDetails(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {}
