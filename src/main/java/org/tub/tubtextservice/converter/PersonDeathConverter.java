package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.domain.persondate.HijriDeath;
import org.tub.tubtextservice.model.domain.persondate.PersonDeath;
import org.tub.tubtextservice.model.domain.persondate.ShamsiDeath;
import org.tub.tubtextservice.model.tubresponse.printouts.AuthorPrintouts;

public class PersonDeathConverter implements Converter<AuthorPrintouts, PersonDeath> {

  final MediaWikiDateConvertor mediaWikiDateConvertor;

  public PersonDeathConverter(MediaWikiDateConvertor mediaWikiDateConvertor) {
    this.mediaWikiDateConvertor = mediaWikiDateConvertor;
  }

  final @Override public PersonDeath convert(AuthorPrintouts source) {
    var hijri = "";
    var shamsi = "";
    var gregorian = "";

    if (!source.deathHijri().isEmpty()) {
      hijri = source.deathHijri().get(0).toString();
    }
    if (!source.deathShamsi().isEmpty()) {
      shamsi = source.deathShamsi().get(0).toString();
    }
    if (!source.deathGregorian().isEmpty()) {
      gregorian = mediaWikiDateConvertor.convert(source.deathGregorian().get(0));
    }

    if (!source.deathHijriText().isEmpty()) {
      hijri = source.deathHijriText().get(0);
    }

    if (!source.deathShamsiText().isEmpty()) {
      shamsi = source.deathShamsiText().get(0);
    }
    if (!source.deathGregorianText().isEmpty()) {
      gregorian = source.deathGregorianText().get(0);
    }
    if (shamsi.isEmpty()) {
      return new HijriDeath(hijri, gregorian);
    }
    return new ShamsiDeath(shamsi, gregorian);
  }
}
