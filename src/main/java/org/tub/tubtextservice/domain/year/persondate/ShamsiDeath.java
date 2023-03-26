package org.tub.tubtextservice.domain.year.persondate;

import org.tub.tubtextservice.usecase.tub.utility.NonNumericTextStripper;

public record ShamsiDeath(String year, String gregorian) implements PersonDeath {

  public ShamsiDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
