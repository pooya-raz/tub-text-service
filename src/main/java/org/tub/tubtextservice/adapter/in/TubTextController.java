package org.tub.tubtextservice.adapter.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.adapter.out.semanticmediawiki.SemanticMediaWikiAdapter;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;

@RestController
@RequestMapping()
public class TubTextController {

  private final SemanticMediaWikiAdapter getTubData;

  public TubTextController(SemanticMediaWikiAdapter getTubData) {
    this.getTubData = getTubData;
  }

  @GetMapping("/entry")
  public EntriesDto getEntry() {
    return getTubData.getEntries();
    }
}
