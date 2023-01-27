package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.domain.persondate.HijriDeath;
import org.tub.tubtextservice.model.domain.persondate.PersonDeath;
import org.tub.tubtextservice.model.domain.persondate.ShamsiDeath;
import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;

import java.util.List;

public class PersonDeathConverter implements Converter<AuthorPrintouts, PersonDeath> {

  final MediaWikiDateConvertor mediaWikiDateConvertor;

  public PersonDeathConverter(MediaWikiDateConvertor mediaWikiDateConvertor) {
    this.mediaWikiDateConvertor = mediaWikiDateConvertor;
  }

  final @Override public PersonDeath convert(AuthorPrintouts source) {
    var hijri = "";
    var shamsi = "";
    var gregorian = "";

    hijri = getYear(source.deathHijri(), source.deathHijriText());
    shamsi = getYear(source.deathShamsi(), source.deathShamsiText());
    gregorian = getGregorianYear(source.deathGregorian(), source.deathGregorianText());

    if (shamsi.isEmpty()) {
      return new HijriDeath(hijri, gregorian);
    }
    return new ShamsiDeath(shamsi, gregorian);
  }

  private String getYear(List<Integer> hijri, List<String> hijriText) {
    var year = "";
    if (!hijri.isEmpty()) {
      year = hijri.get(0).toString();
    }
    if (!hijriText.isEmpty()) {
      year = hijriText.get(0);
    }
    return year;
  }

  private String getGregorianYear(List<MediaWikiDate> gregorian, List<String> gregorianText) {
    var year = "";

    if (!gregorian.isEmpty()) {
      year = mediaWikiDateConvertor.convert(gregorian.get(0));
    }
    if (!gregorianText.isEmpty()) {
      year = gregorianText.get(0);
    }
    return year;
  }
}
