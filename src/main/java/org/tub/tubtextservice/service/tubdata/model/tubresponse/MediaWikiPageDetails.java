package org.tub.tubtextservice.service.tubdata.model.tubresponse;

public record MediaWikiPageDetails(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    String fulltext;

    public Builder fulltext(String fulltext) {
      this.fulltext = fulltext;
      return this;
    }

    public MediaWikiPageDetails build() {
      return new MediaWikiPageDetails(fulltext, null, null, null, null);
    }
  }
}
