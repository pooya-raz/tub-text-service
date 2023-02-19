package org.tub.tubtextservice.service.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.tub.tubtextservice.model.domain.Entry;

import java.util.List;

/** A service that generates word documents from TUB data. */
public class TubWordService {

  /**
   * Generates a Word document
   *
   * @param entries TUB data in domain model
   * @return a word document
   */
  public XWPFDocument createWordDocument(final List<Entry> entries) {
    return new XWPFDocument();
  }
}
