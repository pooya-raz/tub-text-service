package org.tub.tubtextservice.application.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.adapter.semantic.TubSemanticMediaWikiAdapter;
import org.tub.tubtextservice.domain.model.tubentry.TubEntry;

@RestController
@RequestMapping()
public class TubTextController {

  private final TubSemanticMediaWikiAdapter tubSemanticMediaWikiAdapter;

  public TubTextController(TubSemanticMediaWikiAdapter tubSemanticMediaWikiAdapter) {
    this.tubSemanticMediaWikiAdapter = tubSemanticMediaWikiAdapter;
  }

  @GetMapping("/entry")
  public List<TubEntry> getEntry() {
    return tubSemanticMediaWikiAdapter.getEntries();
    }
}
