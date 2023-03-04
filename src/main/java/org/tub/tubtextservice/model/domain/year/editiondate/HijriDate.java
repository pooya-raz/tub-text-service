package org.tub.tubtextservice.model.domain.year.editiondate;

import org.tub.tubtextservice.service.tubdata.utility.NonNumericTextStripper;

public record HijriDate(String year, String gregorian) implements EditionDate {
  public HijriDate {
    gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
  }
}
