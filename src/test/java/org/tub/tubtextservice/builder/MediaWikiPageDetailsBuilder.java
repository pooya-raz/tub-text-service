package org.tub.tubtextservice.builder;

import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.MediaWikiPageDetails;

public class MediaWikiPageDetailsBuilder {
  String fulltext;

  public static MediaWikiPageDetailsBuilder builder() {
    return new MediaWikiPageDetailsBuilder();
  }

  public MediaWikiPageDetailsBuilder fulltext(String fulltext) {
    this.fulltext = fulltext;
    return this;
  }

  public MediaWikiPageDetails build() {
    return new MediaWikiPageDetails(fulltext, null, null, null, null);
  }
}
