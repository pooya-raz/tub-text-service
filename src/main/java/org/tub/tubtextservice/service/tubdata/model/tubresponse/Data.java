package org.tub.tubtextservice.service.tubdata.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.TitlePrintouts;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

/**
 * Collection of data returned by Semantic Mediawiki.
 *
 * @param printouts the semantic data of the Wiki article.
 * @param fullText the title of the Wiki article and a unique identifier
 * @param fullUrl
 * @param namespace
 * @param exists
 * @param displayTitle an alternate title of the page. Generally left blank. @see <a
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
    String fullText,
    String fullUrl,
    Integer namespace,
    String exists,
    String displayTitle) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    Printouts printouts;
    String fullText;

    public Builder printouts(Printouts printouts) {
      this.printouts = printouts;
      return this;
    }

    public Builder fullText(String fullText) {
      this.fullText = fullText;
      return this;
    }

    public Data build() {
      return new Data(printouts, fullText, null, null, null, null);
    }
  }
}
