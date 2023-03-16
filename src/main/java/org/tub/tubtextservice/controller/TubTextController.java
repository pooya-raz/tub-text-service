package org.tub.tubtextservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.service.tubdata.TubDataService;

@RestController
@RequestMapping()
public class TubTextController {

  private final TubDataService tubDataService;

  public TubTextController(TubDataService tubDataService) {
    this.tubDataService = tubDataService;
  }

  @GetMapping("/entry")
  public List<Entry> getEntry() {
    return tubDataService.getEntries();
    }
}
