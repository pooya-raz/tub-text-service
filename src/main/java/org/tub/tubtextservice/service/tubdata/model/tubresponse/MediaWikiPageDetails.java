package org.tub.tubtextservice.service.tubdata.model.tubresponse;

public record MediaWikiPageDetails(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {}
