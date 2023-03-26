package org.tub.tubtextservice.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.adapter.semanticmediawiki.convert.MediaWikiDateConverter;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.response.MediaWikiDate;

class MediaWikiDateConverterTest {
  @Test
  void convertShouldReturnCorrectYear() {

    final var expected = "1905";
    final var mediawikiDate = new MediaWikiDate(-2051222400L, "");
    final var actual = MediaWikiDateConverter.convert(mediawikiDate);
    assertThat(actual).isEqualTo(expected);
  }
}
