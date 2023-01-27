package org.tub.tubtextservice.formatter;

import org.tub.tubtextservice.model.domain.persondate.HijriDeath;
import org.tub.tubtextservice.model.domain.persondate.PersonDeath;
import org.tub.tubtextservice.model.domain.persondate.ShamsiDeath;

public final class DateFormatter {

  public static String format(PersonDeath personDeath) {
    switch (personDeath) {
      case HijriDeath hijriDeath -> {
        return "(d. %s/%s)".formatted(hijriDeath.hijri(), hijriDeath.gregorian());
      }
      case ShamsiDeath shamsiDeath -> {
        return "(d. %sSh/%s)".formatted(shamsiDeath.shamsi(), shamsiDeath.gregorian());
      }
    }
    return null;
  }
}
