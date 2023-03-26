package org.tub.tubtextservice.domain.year.editiondate;

import org.tub.tubtextservice.domain.common.NonNumericTextStripper;

public record ShamsiDate(String year, String gregorian) implements EditionDate {
  public ShamsiDate {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
