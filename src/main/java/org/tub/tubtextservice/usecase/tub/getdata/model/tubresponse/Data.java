package org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.TitlePrintouts;

/**
 * Collection of data returned by Semantic Mediawiki.
 *
 * @param printouts the semantic data of the Wiki article.
 * @param fulltext the title of the Wiki article and a unique identifier
 * @param fullurl the URL of the Wiki article
 * @param namespace the namespace of the Wiki article
 * @param exists whether the Wiki article exists
 * @param displaytitle an alternate title of the page. Generally left blank. @see <a
 *     href="https://www.semantic-mediawiki.org/wiki/Help:Display_title">Semantic Mediawiki -
 *     Help:Display title</a>
 */
public record Data(
    @JsonTypeInfo(use = DEDUCTION)
        @JsonSubTypes({
          @JsonSubTypes.Type(TitlePrintouts.class),
          @JsonSubTypes.Type(AuthorPrintouts.class),
          @JsonSubTypes.Type(ManuscriptPrintouts.class),
          @JsonSubTypes.Type(EditionPrintouts.class)
        })
        Printouts printouts,
    String fulltext,
    String fullurl,
    Integer namespace,
    String exists,
    String displaytitle) {

  public Data {
    if (printouts == null) {
      throw new IllegalArgumentException("printouts must not be null");
    }
    if (fulltext == null) {
      throw new IllegalArgumentException("fulltext must not be null");
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    Printouts printouts;
    String fulltext;

    public Builder printouts(Printouts printouts) {
      this.printouts = printouts;
      return this;
    }

    public Builder fulltext(String fulltext) {
      this.fulltext = fulltext;
      return this;
    }

    public Data build() {
      return new Data(printouts, fulltext, null, null, null, null);
    }
  }
}
