package org.tub.tubtextservice.application.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.domain.model.tubentry.TubEntry;
import org.tub.tubtextservice.domain.tubdata.TubDataService;

@RestController
@RequestMapping()
public class TubTextController {

  private final TubDataService tubDataService;

  public TubTextController(TubDataService tubDataService) {
    this.tubDataService = tubDataService;
  }

  @GetMapping("/entry")
  public List<TubEntry> getEntry() {
    return tubDataService.getEntries();
    }
}
