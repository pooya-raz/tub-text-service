package org.tub.tubtextservice.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.model.tubresponse.printouts.Printouts;
import org.tub.tubtextservice.model.tubresponse.printouts.TitlePrintouts;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

public record Data(
    @JsonTypeInfo(use = DEDUCTION) // Intended usage
        @JsonSubTypes({
          @JsonSubTypes.Type(TitlePrintouts.class),
          @JsonSubTypes.Type(AuthorPrintouts.class)
        })
        Printouts printouts,
    String fullText,
    String fullUrl,
    Integer namespace,
    String exists,
    String displayTitle) {}
