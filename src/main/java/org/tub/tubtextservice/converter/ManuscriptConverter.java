package org.tub.tubtextservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.tub.tubtextservice.model.Manuscript;
import org.tub.tubtextservice.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.model.tubresponse.printouts.ManuscriptPrintouts;

public class ManuscriptConverter implements Converter<ManuscriptPrintouts, Manuscript> {

  private final TubDateConverter tubDateConverter;

  public ManuscriptConverter(TubDateConverter tubDateConverter) {
    this.tubDateConverter = tubDateConverter;
  }

  @Override
  public Manuscript convert(ManuscriptPrintouts manuscriptPrintouts) {
    return new Manuscript(
        manuscriptPrintouts.location().stream().findFirst().orElse(null),
        manuscriptPrintouts.city().stream()
            .findFirst()
            .map(MediaWikiPageDetails::fulltext)
            .orElse(null),
        manuscriptPrintouts.manuscriptNumber().stream().findFirst().orElse(null),
        tubDateConverter.convert(manuscriptPrintouts));
  }
}