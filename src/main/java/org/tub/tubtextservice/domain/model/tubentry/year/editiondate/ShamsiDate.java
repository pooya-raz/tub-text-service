package org.tub.tubtextservice.domain.model.tubentry.year.editiondate;

import org.tub.tubtextservice.adapter.semantic.utility.NonNumericTextStripper;

public record ShamsiDate(String year, String gregorian) implements EditionDate {
  public ShamsiDate {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
