package org.tub.tubtextservice.model.domain.persondate;

import org.tub.tubtextservice.utility.NonNumericTextStripper;

public record ShamsiDeath(String shamsi, String gregorian) implements PersonDeath {

  public ShamsiDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
