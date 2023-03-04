package org.tub.tubtextservice.model.domain.year.persondate;

import org.tub.tubtextservice.service.tubdata.utility.NonNumericTextStripper;

public record ShamsiDeath(String year, String gregorian) implements PersonDeath {

  public ShamsiDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
