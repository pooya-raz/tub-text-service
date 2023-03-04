package org.tub.tubtextservice.builder;

import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiDate;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.AuthorPrintouts;

import java.util.ArrayList;
import java.util.List;

public class AuthorPrintoutsBuilder {

  List<String> fullNameTransliterated = new ArrayList<>();
  List<Integer> deathHijri = new ArrayList<>();
  List<MediaWikiDate> deathGregorian = new ArrayList<>();
  List<Integer> deathShamsi = new ArrayList<>();
  List<String> deathHijriText = new ArrayList<>();
  List<String> deathGregorianText = new ArrayList<>();
  List<String> deathShamsiText = new ArrayList<>();

  public static AuthorPrintoutsBuilder builder() {
    return new AuthorPrintoutsBuilder();
  }

  public AuthorPrintoutsBuilder fullNameTransliterated(String fullNameTransliterated) {
    this.fullNameTransliterated.add(fullNameTransliterated);
    return this;
  }

  public AuthorPrintoutsBuilder deathHijri(Integer deathHijri) {
    this.deathHijri.add(deathHijri);
    return this;
  }

  public AuthorPrintoutsBuilder deathGregorian(Long deathGregorian) {
    this.deathGregorian.add(new MediaWikiDate(deathGregorian, ""));
    return this;
  }

  public AuthorPrintoutsBuilder deathHijriText(String deathHijriText) {
    this.deathHijriText.add(deathHijriText);
    return this;
  }

  public AuthorPrintoutsBuilder deathGregorianText(String deathGregorianText) {
    this.deathGregorianText.add(deathGregorianText);
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
