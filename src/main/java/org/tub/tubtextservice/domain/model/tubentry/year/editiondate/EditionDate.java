package org.tub.tubtextservice.domain.model.tubentry.year.editiondate;

import org.tub.tubtextservice.domain.model.tubentry.year.Year;

public sealed interface EditionDate extends Year permits HijriDate, ShamsiDate {
  @Override
  String year();

  @Override
  String gregorian();
}
