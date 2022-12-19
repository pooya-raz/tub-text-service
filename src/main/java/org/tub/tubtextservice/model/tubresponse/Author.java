package org.tub.tubtextservice.model.tubresponse;

public record Author(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {}
