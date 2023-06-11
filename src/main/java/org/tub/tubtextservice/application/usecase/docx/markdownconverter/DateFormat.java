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
        var prependedText = createPrependText(personDeath);
        var nonGregorian = createNonGregorian(personDeath);
        final var gregorian = personDeath.gregorian();

        return "(" + prependedText + nonGregorian + "/" + gregorian + ")";
    }

    private static String createPrependText(final PersonDeath personDeath) {
        var prependedText = "";
        if (personDeath.year().startsWith("c.")
                || personDeath.year().startsWith("after")
                || personDeath.year().startsWith("before")
                || (!personDeath.year().isBlank()
                        && Character.isDigit(personDeath.year().charAt(0)))) {
            prependedText = "d. ";
        }
        return prependedText;
    }

    private static String createNonGregorian(final PersonDeath personDeath) {
        var nonGregorian = personDeath.year();
        if (personDeath instanceof ShamsiDeath) {
            nonGregorian = nonGregorian + "Sh";
        }
        if (personDeath.year().startsWith("c.")) {
            nonGregorian = nonGregorian.replace("c.", "*c.*");
        }
        return nonGregorian;
    }
}
