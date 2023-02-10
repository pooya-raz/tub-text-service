package org.tub.tubtextservice.formatter;

import org.tub.tubtextservice.model.domain.persondate.PersonDeath;
import org.tub.tubtextservice.model.domain.persondate.ShamsiDeath;

public final class DateFormatter {

  public static String format(PersonDeath personDeath) {
    final var template = createTemplate(personDeath);
    return template.formatted(personDeath.year(), personDeath.gregorian());
  }

  private static String createTemplate(PersonDeath personDeath) {
    var prependedText = "d. ";
    var nonGregorian = "%s";
    final var gregorian = "%s";
    if(personDeath instanceof ShamsiDeath){
      nonGregorian = gregorian + "Sh";
    }
    if(personDeath.year().startsWith("fl. ")){
      prependedText = "";
    }
    return "(" + prependedText + nonGregorian + "/" + gregorian + ")";
  }
}
