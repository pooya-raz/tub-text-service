package org.tub.tubtextservice.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;

import java.util.List;

public record ManuscriptPrintouts(
    @JsonProperty("Has a location") List<String> location,
    @JsonProperty("Has references") List<String> references,
    @JsonProperty("Has year(Gregorian)") List<Integer> yearGregorian,
    @JsonProperty("Has year(Gregorian) text") List<String> yearGregorianText,
    @JsonProperty("Has year(Hijri)") List<Integer> yearHijri,
    @JsonProperty("Has year(Hijri) text") List<String> yearHijriText,
    @JsonProperty("Has year(Shamsi)") List<Integer> yearShamsi,
    @JsonProperty("Has year(Shamsi) text") List<String> yearShamsiText,
    @JsonProperty("Located in a city") List<MediaWikiPageDetails> city,
    @JsonProperty("Manuscript number") List<String> manuscriptNumber,
    @JsonProperty("Manuscript of title") List<MediaWikiPageDetails> manuscriptOfTitle)
    implements Printouts {}
