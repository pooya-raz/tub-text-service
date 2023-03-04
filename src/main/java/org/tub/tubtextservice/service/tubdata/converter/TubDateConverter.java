package org.tub.tubtextservice.service.tubdata.converter;

import org.springframework.stereotype.Component;
import org.tub.tubtextservice.model.domain.year.editiondate.EditionDate;
import org.tub.tubtextservice.model.domain.year.editiondate.HijriDate;
import org.tub.tubtextservice.model.domain.year.editiondate.ShamsiDate;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.model.domain.year.persondate.PersonDeath;
import org.tub.tubtextservice.model.domain.year.persondate.ShamsiDeath;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.MediaWikiDate;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.AuthorPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.DatedPrintouts;

import java.util.List;

@Component
public class TubDateConverter {

  public EditionDate convert(DatedPrintouts printouts) {
    final var hijri = getYear(printouts.yearHijri(), printouts.yearHijriText());
    final var shamsi = getYear(printouts.yearShamsi(), printouts.yearShamsiText());
    final var gregorian = getYear(printouts.yearGregorian(), printouts.yearGregorianText());

    if (!shamsi.isEmpty()) {
      return new ShamsiDate(shamsi, gregorian);
    }
    return new HijriDate(hijri, gregorian);
  }

  public PersonDeath convert(AuthorPrintouts source) {
    final var hijri = getYear(source.deathHijri(), source.deathHijriText());
    final var shamsi = getYear(source.deathShamsi(), source.deathShamsiText());
    final var gregorian =
        getYearFromMediaWikiDate(source.deathGregorian(), source.deathGregorianText());

    if (!shamsi.isEmpty()) {
      return new ShamsiDeath(shamsi, gregorian);
    }
    return new HijriDeath(hijri, gregorian);
  }

  private String getYear(List<Integer> year, List<String> yearText) {
    var result = "";
    if (!year.isEmpty() && year.get(0) != null) {
      result = year.get(0).toString();
    }
    if (!yearText.isEmpty()) {
      result = yearText.get(0);
    }
    return result;
  }

  private String getYearFromMediaWikiDate(
      List<MediaWikiDate> gregorian, List<String> gregorianText) {
    var year = "";

    if (!gregorian.isEmpty()) {
      year = MediaWikiDateConverter.convert(gregorian.get(0));
    }
    if (!gregorianText.isEmpty()) {
      year = gregorianText.get(0);
    }
    return year;
  }
}
