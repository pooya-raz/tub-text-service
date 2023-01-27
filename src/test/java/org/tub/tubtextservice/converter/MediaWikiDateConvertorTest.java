package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.model.tubresponse.MediaWikiDate;

import static org.assertj.core.api.Assertions.assertThat;

class MediaWikiDateConvertorTest {
  private final MediaWikiDateConvertor mediaWikiDateConvertor = new MediaWikiDateConvertor();

  @Test
  void convertShouldReturnCorrectYear() {

    final var expected = 1905;
    final var mediawikiDate = new MediaWikiDate(-2051222400L, "");
    final var actual = mediaWikiDateConvertor.convert(mediawikiDate);
    assertThat(actual).isEqualTo(expected);
  }
}
