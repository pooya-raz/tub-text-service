package org.tub.tubtextservice.control;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.tub.tubtextservice.service.tubapi.service.TubApiService;
import org.tub.tubtextservice.service.word.TubWordService;

/** Workflow for generating a Word document from the TUB API. */
public class TubWordControl {

  private final TubApiService tubApiService;
  private final TubWordService tubWordService;

  public TubWordControl(TubApiService tubApiService, TubWordService tubWordService) {
    this.tubApiService = tubApiService;
    this.tubWordService = tubWordService;
  }

  /**
   * Generates a Word document from the TUB API.
   *
   * @return a Word document
   */
  public XWPFDocument createWordDocumentFromTub() {
    final var tubData = tubApiService.getData();
    return tubWordService.createWordDocument(tubData);
  }
}
