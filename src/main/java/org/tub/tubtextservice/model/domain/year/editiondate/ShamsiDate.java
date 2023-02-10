package org.tub.tubtextservice.model.domain.year.editiondate;

import org.tub.tubtextservice.utility.NonNumericTextStripper;

public record ShamsiDate(String year, String gregorian) implements EditionDate {
    public ShamsiDate {
        gregorian = NonNumericTextStripper.stripNonNumericText(gregorian);
    }
}
