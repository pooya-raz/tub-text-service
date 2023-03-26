package org.tub.tubtextservice.domain.model.tubentry.year.persondate;

import org.tub.tubtextservice.adapter.semantic.utility.NonNumericTextStripper;

public record ShamsiDeath(String year, String gregorian) implements PersonDeath {

  public ShamsiDeath {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
