package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.domain.year.persondate.HijriDeath;
import org.tub.tubtextservice.model.domain.year.persondate.PersonDeath;
import org.tub.tubtextservice.model.domain.year.persondate.ShamsiDeath;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;

public class PersonDeathConverter implements Converter<AuthorPrintouts, PersonDeath> {

  final @Override public PersonDeath convert(AuthorPrintouts source) {
    var hijri = "";
    var shamsi = "";
    var gregorian = "";

    hijri = YearToStringConvertor.convert(source.deathHijri(), source.deathHijriText());
    shamsi = YearToStringConvertor.convert(source.deathShamsi(), source.deathShamsiText());
    gregorian = YearToStringConvertor.convertMediaWikiDate(source.deathGregorian(), source.deathGregorianText());

    if (shamsi.isEmpty()) {
      return new HijriDeath(hijri, gregorian);
    }
    return new ShamsiDeath(shamsi, gregorian);
  }


}
