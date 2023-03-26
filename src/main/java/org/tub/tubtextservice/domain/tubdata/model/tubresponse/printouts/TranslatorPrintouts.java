package org.tub.tubtextservice.domain.tubdata.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.domain.tubdata.model.tubresponse.MediaWikiDate;

import java.util.List;

@SuppressWarnings({"findbugs:EI_EXPOSE_REP", "findbugs:EI_EXPOSE_REP2"})
public record TranslatorPrintouts(
    @JsonProperty("Full name (transliterated)") List<String> fullNameTransliterated,
    @JsonProperty("Death (Hijri)") List<Integer> deathHijri,
    @JsonProperty("Death (Gregorian)") List<MediaWikiDate> deathGregorian,
    @JsonProperty("Death (Shamsi)") List<Integer> deathShamsi,
    @JsonProperty("Death (Hijri) text") List<String> deathHijriText,
    @JsonProperty("Death (Gregorian) text") List<String> deathGregorianText,
    @JsonProperty("Death (Shamsi) text") List<String> deathShamsiText)
    implements PersonPrintouts {}
