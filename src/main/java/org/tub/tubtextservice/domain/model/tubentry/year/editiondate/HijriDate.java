package org.tub.tubtextservice.domain.model.tubentry.year.editiondate;

import org.tub.tubtextservice.adapter.semantic.utility.NonNumericTextStripper;

public record HijriDate(String year, String gregorian) implements EditionDate {
  public HijriDate {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
