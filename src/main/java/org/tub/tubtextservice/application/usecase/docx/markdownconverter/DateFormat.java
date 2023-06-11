package org.tub.tubtextservice.application.usecase.docx.markdownconverter;

import org.tub.tubtextservice.domain.year.persondate.PersonDeath;
import org.tub.tubtextservice.domain.year.persondate.ShamsiDeath;

class DateFormat {

    private DateFormat() {
        throw new UnsupportedOperationException("DateFormat is a utility class and cannot be instantiated");
    }

    static String create(final PersonDeath personDeath) {
        if (personDeath == null
                || (personDeath.year().isBlank() && personDeath.gregorian().isBlank())) {
            return "";
        }
        var prependedText = "";
        var nonGregorian = personDeath.year();
        final var gregorian = personDeath.gregorian();
        if (personDeath instanceof ShamsiDeath) {
            nonGregorian = nonGregorian + "Sh";
        }

        if (personDeath.year().startsWith("after")
                || personDeath.year().startsWith("before")
                || (!personDeath.year().isBlank()
                        && Character.isDigit(personDeath.year().charAt(0)))) {
            prependedText = "d. ";
        }
        if (personDeath.year().startsWith("c.")) {
            nonGregorian = nonGregorian.replace("c.", "*c.*");
            prependedText = "d. ";
        }
        return "(" + prependedText + nonGregorian + "/" + gregorian + ")";
    }
}
