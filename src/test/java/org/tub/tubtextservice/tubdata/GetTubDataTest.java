package org.tub.tubtextservice.tubdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.adapter.semanticmediawiki.SemanticMediaWikiAdapter;
import org.tub.tubtextservice.adapter.semanticmediawiki.PrintoutsManager;
import org.tub.tubtextservice.adapter.semanticmediawiki.convert.ConvertToEntry;
import org.tub.tubtextservice.builder.TitlePrintoutsBuilder;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.adapter.semanticmediawiki.TubPrintouts;

@ExtendWith(MockitoExtension.class)
class GetTubDataTest {

  private SemanticMediaWikiAdapter subject;

  @Mock private PrintoutsManager printoutsManager;
  @Mock private ConvertToEntry convertToEntry;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new SemanticMediaWikiAdapter(printoutsManager, convertToEntry);
  }

  @Test
  void getEntriesShouldGetEntries() {
    final var title = TitlePrintoutsBuilder.builder().titleTransliterated("Title").build();
    final var printouts = TubPrintouts.builder().titles("Title", title).build();
    final var expected = List.of(TubEntry.builder().titleTransliterated("Title").build());
    Mockito.when(printoutsManager.getPrintouts()).thenReturn(printouts);
    Mockito.when(convertToEntry.convert(printouts)).thenReturn(expected);
    assertThat(subject.getEntries()).isEqualTo(expected);
  }
}
