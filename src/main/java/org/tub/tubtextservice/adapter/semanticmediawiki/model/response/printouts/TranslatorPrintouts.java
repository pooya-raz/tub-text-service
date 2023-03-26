package org.tub.tubtextservice.adapter.semanticmediawiki.model.response.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.MediaWikiDate;

public record TranslatorPrintouts(
    @JsonProperty("Full name (transliterated)") List<String> fullNameTransliterated,
    @JsonProperty("Death (Hijri)") List<Integer> deathHijri,
    @JsonProperty("Death (Gregorian)") List<MediaWikiDate> deathGregorian,
    @JsonProperty("Death (Shamsi)") List<Integer> deathShamsi,
    @JsonProperty("Death (Hijri) text") List<String> deathHijriText,
    @JsonProperty("Death (Gregorian) text") List<String> deathGregorianText,
    @JsonProperty("Death (Shamsi) text") List<String> deathShamsiText)
    implements PersonPrintouts {}
