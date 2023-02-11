package org.tub.tubtextservice.model.domain.year.persondate;

import org.tub.tubtextservice.service.tubapi.utility.NonNumericTextStripper;

public record HijriDeath(String year, String gregorian) implements PersonDeath {

  public HijriDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
