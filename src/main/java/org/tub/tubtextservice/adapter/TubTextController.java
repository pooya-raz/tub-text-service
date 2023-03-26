package org.tub.tubtextservice.adapter;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.adapter.semanticmediawiki.SemanticMediaWikiAdapter;

@RestController
@RequestMapping()
public class TubTextController {

  private final SemanticMediaWikiAdapter getTubData;

  public TubTextController(SemanticMediaWikiAdapter getTubData) {
    this.getTubData = getTubData;
  }

  @GetMapping("/entry")
  public List<TubEntry> getEntry() {
    return getTubData.getEntries();
    }
}
