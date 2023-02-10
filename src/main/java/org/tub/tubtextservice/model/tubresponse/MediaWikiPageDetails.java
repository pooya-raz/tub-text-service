package org.tub.tubtextservice.model.tubresponse;

public record MediaWikiPageDetails(
    String fulltext, String fullurl, Integer namespace, String exists, String displaytitle) {

    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        String fulltext;
        String fullurl;
        Integer namespace;
        String exists;
        String displaytitle;

        public Builder() {}

        public Builder fulltext(String fulltext) {
            this.fulltext = fulltext;
            return this;
        }

        public Builder fullurl(String fullurl) {
            this.fullurl = fullurl;
            return this;
        }

        public Builder namespace(Integer namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder exists(String exists) {
            this.exists = exists;
            return this;
        }

        public Builder displaytitle(String displaytitle) {
            this.displaytitle = displaytitle;
            return this;
        }

        public MediaWikiPageDetails build() {
            return new MediaWikiPageDetails(fulltext, fullurl, namespace, exists, displaytitle);
        }
    }
}
