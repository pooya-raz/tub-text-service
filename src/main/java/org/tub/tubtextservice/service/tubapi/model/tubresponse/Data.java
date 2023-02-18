package org.tub.tubtextservice.service.tubapi.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts.TitlePrintouts;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

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
    String fullUrl;
    Integer namespace;
    String exists;
    String displayTitle;

    public Builder printouts(Printouts printouts) {
      this.printouts = printouts;
      return this;
    }

    public Builder fullText(String fullText) {
      this.fullText = fullText;
      return this;
    }

    public Data build() {
      return new Data(printouts, fullText, fullUrl, namespace, exists, displayTitle);
    }
  }
}
