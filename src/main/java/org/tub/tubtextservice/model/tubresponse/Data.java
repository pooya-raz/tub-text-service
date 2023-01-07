package org.tub.tubtextservice.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.EditionPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;

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
    String displayTitle) {}
