package org.tub.tubtextservice.builder;

import java.util.ArrayList;
import java.util.List;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.EditionPrintouts;

public class EditionPrintoutsBuilder {
  List<String> titleTransliterated = new ArrayList<>();
  List<String> titleArabic = new ArrayList<>();
  List<Integer> yearGregorian = new ArrayList<>();
  List<String> yearGregorianText = new ArrayList<>();
  List<Integer> yearHijri = new ArrayList<>();
  List<String> yearHijriText = new ArrayList<>();
  List<Integer> yearShamsi = new ArrayList<>();
  List<String> yearShamsiText = new ArrayList<>();
  List<MediaWikiPageDetails> publishedEditionOfTitle = new ArrayList<>();
  List<String> editors = new ArrayList<>();
  List<String> publisher = new ArrayList<>();
  List<String> description = new ArrayList<>();
  List<String> editionType = new ArrayList<>();
  List<MediaWikiPageDetails> city = new ArrayList<>();

  public static EditionPrintoutsBuilder builder() {
    return new EditionPrintoutsBuilder();
  }

  public EditionPrintoutsBuilder titleTransliterated(String titleTransliterated) {
    this.titleTransliterated.add(titleTransliterated);
    return this;
  }

  public EditionPrintoutsBuilder titleArabic(String titleArabic) {
    this.titleArabic.add(titleArabic);
    return this;
  }

  public EditionPrintoutsBuilder yearGregorian(Integer yearGregorian) {
    this.yearGregorian.add(yearGregorian);
    return this;
  }

  public EditionPrintoutsBuilder yearGregorianText(String yearGregorianText) {
    this.yearGregorianText.add(yearGregorianText);
    return this;
  }

  public EditionPrintoutsBuilder yearHijri(Integer yearHijri) {
    this.yearHijri.add(yearHijri);
    return this;
  }

  public EditionPrintoutsBuilder yearHijriText(String yearHijriText) {
    this.yearHijriText.add(yearHijriText);
    return this;
  }

  public EditionPrintoutsBuilder yearShamsi(Integer yearShamsi) {
    this.yearShamsi.add(yearShamsi);
    return this;
  }

  public EditionPrintoutsBuilder yearShamsiText(String yearShamsiText) {
    this.yearShamsiText.add(yearShamsiText);
    return this;
  }

  public EditionPrintoutsBuilder publishedEditionOfTitle(String title) {
    final var mediaWikiPageDetails = MediaWikiPageDetailsBuilder.builder().fulltext(title).build();
    this.publishedEditionOfTitle.add(mediaWikiPageDetails);
    return this;
  }

  public EditionPrintoutsBuilder editors(String editors) {
    this.editors.add(editors);
    return this;
  }

  public EditionPrintoutsBuilder publisher(String publisher) {
    this.publisher.add(publisher);
    return this;
  }

  public EditionPrintoutsBuilder description(String description) {
    this.description.add(description);
    return this;
  }

  public EditionPrintoutsBuilder editionType(String editionType) {
    this.editionType.add(editionType);
    return this;
  }

  public EditionPrintoutsBuilder city(MediaWikiPageDetails city) {
    this.city.add(city);
    return this;
  }

  public EditionPrintouts build() {
    return new EditionPrintouts(
        titleTransliterated,
        titleArabic,
        yearGregorian,
        yearGregorianText,
        yearHijri,
        yearHijriText,
        yearShamsi,
        yearShamsiText,
        publishedEditionOfTitle,
        editors,
        publisher,
        description,
        editionType,
        city);
  }
}
