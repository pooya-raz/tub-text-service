package org.tub.tubtextservice.domain.year.persondate;

import org.tub.tubtextservice.usecase.tub.utility.NonNumericTextStripper;

public record HijriDeath(String year, String gregorian) implements PersonDeath {

  public HijriDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}