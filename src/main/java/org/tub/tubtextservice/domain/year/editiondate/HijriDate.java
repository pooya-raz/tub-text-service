package org.tub.tubtextservice.domain.year.editiondate;

import org.tub.tubtextservice.usecase.tub.utility.NonNumericTextStripper;

public record HijriDate(String year, String gregorian) implements EditionDate {
  public HijriDate {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
