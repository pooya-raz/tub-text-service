package org.tub.tubtextservice.domain.model.tubentry.year.persondate;

import org.tub.tubtextservice.domain.tubdata.utility.NonNumericTextStripper;

public record HijriDeath(String year, String gregorian) implements PersonDeath {

  public HijriDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
