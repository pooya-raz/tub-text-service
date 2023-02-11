package org.tub.tubtextservice.model.domain.year.persondate;

import org.tub.tubtextservice.service.tubapi.utility.NonNumericTextStripper;

public record ShamsiDeath(String year, String gregorian) implements PersonDeath {

  public ShamsiDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
