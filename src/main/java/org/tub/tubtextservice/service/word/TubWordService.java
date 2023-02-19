package org.tub.tubtextservice.service.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.tub.tubtextservice.service.tubapi.model.TubPrintouts;

/** A service that generates word documents from TUB data. */
public class TubWordService {

  /**
   * Generates a Word document
   *
   * @param tubPrintOuts TUB data in domain model
   * @return a word document
   */
  public XWPFDocument createWordDocument(final TubPrintouts tubPrintOuts) {
    return new XWPFDocument();
  }
}
