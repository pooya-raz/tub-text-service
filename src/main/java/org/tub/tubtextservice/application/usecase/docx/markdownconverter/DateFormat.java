package org.tub.tubtextservice.application.usecase.docx.markdownconverter;

import org.tub.tubtextservice.domain.year.persondate.PersonDeath;
import org.tub.tubtextservice.domain.year.persondate.ShamsiDeath;

class DateFormat {

    private DateFormat() {
        throw new UnsupportedOperationException("DateFormat is a utility class and cannot be instantiated");
    }

    static String create(final PersonDeath personDeath) {
        final var template = createTemplate(personDeath);
        return template.formatted(personDeath.year(), personDeath.gregorian());
    }

    private static String createTemplate(final PersonDeath personDeath) {
        var prependedText = "";
        var nonGregorian = "%s";
        final var gregorian = "%s";
        if (personDeath instanceof ShamsiDeath) {
            nonGregorian = nonGregorian+ "Sh";
        }
        if (personDeath.year().startsWith("after") || personDeath.year().startsWith("before")) {
            prependedText = "d. ";
        }
        return "(" + prependedText + nonGregorian + "/" + gregorian + ")";
    }
}