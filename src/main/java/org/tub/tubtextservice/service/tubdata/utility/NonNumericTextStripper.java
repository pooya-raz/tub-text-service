package org.tub.tubtextservice.service.tubdata.utility;

import org.apache.commons.lang3.StringUtils;

public final class NonNumericTextStripper {
  private NonNumericTextStripper() {
    throw new UnsupportedOperationException(
        "NonNumericTextStripper is a utility class and cannot be instantiated");
  }

  public static String stripNonNumericText(String text) {
    if(StringUtils.isBlank(text)){
      return text;
    }
    final var chars = text.toCharArray();
    final var firstNumericIndex = findFirstNumericIndex(chars);
    return text.substring(firstNumericIndex);
  }

  private static int findFirstNumericIndex(char[] chars) {
    for (int i = 0; i < chars.length; i++) {
      if (Character.isDigit(chars[i])) {
        return i;
      }
    }
    return -1;
  }
}
