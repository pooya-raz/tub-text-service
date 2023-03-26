package org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse;

public record MediaWikiPageDetails(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {}
