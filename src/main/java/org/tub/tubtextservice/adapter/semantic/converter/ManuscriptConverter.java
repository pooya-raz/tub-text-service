package org.tub.tubtextservice.adapter.semantic.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tub.tubtextservice.adapter.semantic.model.tubresponse.MediaWikiPageDetails;
import org.tub.tubtextservice.adapter.semantic.model.tubresponse.printouts.ManuscriptPrintouts;
import org.tub.tubtextservice.domain.model.tubentry.Manuscript;

@Component
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
