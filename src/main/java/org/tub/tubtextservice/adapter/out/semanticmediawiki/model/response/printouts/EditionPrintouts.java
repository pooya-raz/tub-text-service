package org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.model.response.MediaWikiPageDetails;

@SuppressWarnings({"findbugs:EI_EXPOSE_REP", "findbugs:EI_EXPOSE_REP2"})
public record EditionPrintouts(
        @JsonProperty("Title (transliterated)") List<String> titleTransliterated,
        @JsonProperty("Title (Arabic)") List<String> titleArabic,
        @JsonProperty("Has year(Gregorian)") List<Integer> yearGregorian,
        @JsonProperty("Has year(Gregorian) text") List<String> yearGregorianText,
        @JsonProperty("Has year(Hijri)") List<Integer> yearHijri,
        @JsonProperty("Has year(Hijri) text") List<String> yearHijriText,
        @JsonProperty("Has year(Shamsi)") List<Integer> yearShamsi,
        @JsonProperty("Has year(Shamsi) text") List<String> yearShamsiText,
        @JsonProperty("Published edition of title") List<MediaWikiPageDetails> editionOfTitle,
        @JsonProperty("Has editor(s)") List<String> editors,
        @JsonProperty("Has a publisher") List<String> publisher,
        @JsonProperty("Has a description") List<String> description,
        @JsonProperty("Edition type") List<String> editionType,
        @JsonProperty("City") List<MediaWikiPageDetails> city)
        implements DatedPrintouts {}
