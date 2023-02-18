package org.tub.tubtextservice.service.tubapi.model.tubresponse.printouts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.MediaWikiDate;

import java.util.ArrayList;
import java.util.List;

public record AuthorPrintouts(
    @JsonProperty("Full name (transliterated)") List<String> fullNameTransliterated,
    @JsonProperty("Death (Hijri)") List<Integer> deathHijri,
    @JsonProperty("Death (Gregorian)") List<MediaWikiDate> deathGregorian,
    @JsonProperty("Death (Shamsi)") List<Integer> deathShamsi,
    @JsonProperty("Death (Hijri) text") List<String> deathHijriText,
    @JsonProperty("Death (Gregorian) text") List<String> deathGregorianText,
    @JsonProperty("Death (Shamsi) text") List<String> deathShamsiText)
    implements PersonPrintouts {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    List<String> fullNameTransliterated = new ArrayList<>();
    List<Integer> deathHijri = new ArrayList<>();
    List<MediaWikiDate> deathGregorian = new ArrayList<>();
    List<Integer> deathShamsi = new ArrayList<>();
    List<String> deathHijriText = new ArrayList<>();
    List<String> deathGregorianText = new ArrayList<>();
    List<String> deathShamsiText = new ArrayList<>();

    public Builder() {}

    public Builder fullNameTransliterated(String fullNameTransliterated) {
      this.fullNameTransliterated.add(fullNameTransliterated);
      return this;
    }

    public Builder deathHijri(Integer deathHijri) {
      this.deathHijri.add(deathHijri);
      return this;
    }

    public Builder deathGregorian(Long deathGregorian) {
      this.deathGregorian.add(new MediaWikiDate(deathGregorian, ""));
      return this;
    }

    public Builder deathShamsi(Integer deathShamsi) {
      this.deathShamsi.add(deathShamsi);
      return this;
    }

    public Builder deathHijriText(String deathHijriText) {
      this.deathHijriText.add(deathHijriText);
      return this;
    }

    public Builder deathGregorianText(String deathGregorianText) {
      this.deathGregorianText.add(deathGregorianText);
      return this;
    }

    public Builder deathShamsiText(String deathShamsiText) {
      this.deathShamsiText.add(deathShamsiText);
      return this;
    }

    public AuthorPrintouts build() {
      return new AuthorPrintouts(
          fullNameTransliterated,
          deathHijri,
          deathGregorian,
          deathShamsi,
          deathHijriText,
          deathGregorianText,
          deathShamsiText);
    }
  }
}
