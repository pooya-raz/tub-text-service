package org.tub.tubtextservice.model.domain.persondate;

import org.apache.commons.lang3.StringUtils;
import org.tub.tubtextservice.utility.NonNumericTextStripper;

public record HijriDeath(String hijri, String gregorian) implements PersonDeath {

  public HijriDeath {
    if(StringUtils.isBlank(gregorian)) {
      throw new IllegalArgumentException("Gregorian year is blank");
    }
    if(StringUtils.isBlank(hijri)) {
      throw new IllegalArgumentException("Hijri year is blank");
    }
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
