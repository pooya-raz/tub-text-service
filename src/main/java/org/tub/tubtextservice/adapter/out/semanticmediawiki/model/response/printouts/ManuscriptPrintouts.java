package org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.MediaWikiPageDetails;

@SuppressWarnings({"findbugs:EI_EXPOSE_REP", "findbugs:EI_EXPOSE_REP2"})
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
    implements DatedPrintouts {}
