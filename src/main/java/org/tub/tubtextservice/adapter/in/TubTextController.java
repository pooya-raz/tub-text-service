package org.tub.tubtextservice.adapter.in;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.SemanticMediaWikiAdapter;
import org.tub.tubtextservice.domain.TubEntry;

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
