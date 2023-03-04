package org.tub.tubtextservice.model.domain.year.persondate;

import org.tub.tubtextservice.service.tubdata.utility.NonNumericTextStripper;

public record HijriDeath(String year, String gregorian) implements PersonDeath {

  public HijriDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
