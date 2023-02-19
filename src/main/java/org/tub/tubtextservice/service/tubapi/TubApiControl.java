package org.tub.tubtextservice.service.tubapi;

import org.tub.tubtextservice.service.tubapi.model.TubPrintOuts;
import org.tub.tubtextservice.service.tubapi.service.TubApiService;

import java.util.List;

public class TubApiControl {

  private final TubApiService tubApiService;

  public TubApiControl(TubApiService tubApiService) {
    this.tubApiService = tubApiService;
  }

  public List<TubPrintOuts> getPrintouts() {
    return null;
  }
}
