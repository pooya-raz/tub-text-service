package org.tub.tubtextservice.domain.markdown.formatter;

import org.tub.tubtextservice.domain.model.tubentry.year.persondate.PersonDeath;
import org.tub.tubtextservice.domain.model.tubentry.year.persondate.ShamsiDeath;

public final class DateFormatter {

  private DateFormatter() {
    throw new IllegalStateException("Utility class and cannot be instantiated");
  }

  public static String format(PersonDeath personDeath) {
    final var template = createTemplate(personDeath);
    return template.formatted(personDeath.year(), personDeath.gregorian());
  }

  private static String createTemplate(PersonDeath personDeath) {
    var prependedText = "d. ";
    var nonGregorian = "%s";
    final var gregorian = "%s";
    if (personDeath instanceof ShamsiDeath) {
      nonGregorian = gregorian + "Sh";
    }
    if (personDeath.year().startsWith("fl. ")) {
      prependedText = "";
    }
    return "(" + prependedText + nonGregorian + "/" + gregorian + ")";
  }
}
