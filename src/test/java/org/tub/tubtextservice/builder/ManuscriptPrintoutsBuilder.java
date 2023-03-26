package org.tub.tubtextservice.builder;

import java.util.ArrayList;
import java.util.List;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.usecase.tub.getdata.model.tubresponse.printouts.ManuscriptPrintouts;

public class ManuscriptPrintoutsBuilder {

  List<String> location = new ArrayList<>();
  List<String> references = new ArrayList<>();
  List<Integer> yearGregorian = new ArrayList<>();
  List<String> yearGregorianText = new ArrayList<>();
  List<Integer> yearHijri = new ArrayList<>();
  List<String> yearHijriText = new ArrayList<>();
  List<Integer> yearShamsi = new ArrayList<>();
  List<String> yearShamsiText = new ArrayList<>();
  List<MediaWikiPageDetails> city = new ArrayList<>();
  List<String> manuscriptNumber = new ArrayList<>();
  List<MediaWikiPageDetails> manuscriptOfTitle = new ArrayList<>();

  public static ManuscriptPrintoutsBuilder builder() {
    return new ManuscriptPrintoutsBuilder();
  }

  public ManuscriptPrintoutsBuilder location(String location) {
    this.location.add(location);
    return this;
  }

  public ManuscriptPrintoutsBuilder yearGregorian(Integer yearGregorian) {
    this.yearGregorian.add(yearGregorian);
    return this;
  }

  public ManuscriptPrintoutsBuilder yearHijri(Integer yearHijri) {
    this.yearHijri.add(yearHijri);
    return this;
  }

  public ManuscriptPrintoutsBuilder city(String city) {
    this.city.add(MediaWikiPageDetailsBuilder.builder().fulltext(city).build());
    return this;
  }

  public ManuscriptPrintoutsBuilder manuscriptNumber(String manuscriptNumber) {
    this.manuscriptNumber.add(manuscriptNumber);
    return this;
  }

  public ManuscriptPrintoutsBuilder manuscriptOfTitle(String title) {
    final var page = MediaWikiPageDetailsBuilder.builder().fulltext(title).build();
    this.manuscriptOfTitle.add(page);
    return this;
  }

  public ManuscriptPrintouts build() {
    return new ManuscriptPrintouts(
        location,
        references,
        yearGregorian,
        yearGregorianText,
        yearHijri,
        yearHijriText,
        yearShamsi,
        yearShamsiText,
        city,
        manuscriptNumber,
        manuscriptOfTitle);
  }
}
