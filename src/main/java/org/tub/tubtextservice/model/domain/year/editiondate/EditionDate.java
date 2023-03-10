package org.tub.tubtextservice.model.domain.year.editiondate;

import org.tub.tubtextservice.model.domain.year.Year;

public sealed interface EditionDate extends Year permits HijriDate, ShamsiDate {
  @Override
  String year();

  @Override
  String gregorian();
}
