package org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiDate;

import java.util.List;

public record TranslatorPrintouts(
    @JsonProperty("Full name (transliterated)") List<String> fullNameTransliterated,
    @JsonProperty("Death (Hijri)") List<Integer> deathHijri,
    @JsonProperty("Death (Gregorian)") List<MediaWikiDate> deathGregorian,
    @JsonProperty("Death (Shamsi)") List<Integer> deathShamsi,
    @JsonProperty("Death (Hijri) text") List<String> deathHijriText,
    @JsonProperty("Death (Gregorian) text") List<String> deathGregorianText,
    @JsonProperty("Death (Shamsi) text") List<String> deathShamsiText)
    implements PersonPrintouts {

    public TranslatorPrintouts {
        fullNameTransliterated = List.copyOf(fullNameTransliterated);
        deathHijri = List.copyOf(deathHijri);
        deathGregorian = List.copyOf(deathGregorian);
        deathShamsi = List.copyOf(deathShamsi);
        deathHijriText = List.copyOf(deathHijriText);
        deathGregorianText = List.copyOf(deathGregorianText);
        deathShamsiText = List.copyOf(deathShamsiText);
    }
}
