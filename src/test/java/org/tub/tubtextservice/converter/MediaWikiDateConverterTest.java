package org.tub.tubtextservice.converter;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.service.tubapi.model.tubresponse.MediaWikiDate;
import org.tub.tubtextservice.service.tubapi.converter.MediaWikiDateConverter;

import static org.assertj.core.api.Assertions.assertThat;

class MediaWikiDateConverterTest {
  @Test
  void convertShouldReturnCorrectYear() {

    final var expected = "1905";
    final var mediawikiDate = new MediaWikiDate(-2051222400L, "");
    final var actual = MediaWikiDateConverter.convert(mediawikiDate);
    assertThat(actual).isEqualTo(expected);
  }
}
