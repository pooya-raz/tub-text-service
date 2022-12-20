package org.tub.tubtextservice.model.tubresponse;

public record Category(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {}
