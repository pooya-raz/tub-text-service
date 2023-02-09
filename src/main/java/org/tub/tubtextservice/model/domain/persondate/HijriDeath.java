package org.tub.tubtextservice.model.domain.persondate;

import org.tub.tubtextservice.utility.NonNumericTextStripper;

public record HijriDeath(String hijri, String gregorian) implements PersonDeath {

  public HijriDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
