package org.tub.tubtextservice.tubdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.adapter.semantic.TubSemanticMediaWikiAdapter;
import org.tub.tubtextservice.adapter.semantic.converter.EntryConverter;
import org.tub.tubtextservice.adapter.semantic.model.TubPrintouts;
import org.tub.tubtextservice.adapter.semantic.service.TubApiService;
import org.tub.tubtextservice.builder.TitlePrintoutsBuilder;
import org.tub.tubtextservice.domain.model.tubentry.TubEntry;

@ExtendWith(MockitoExtension.class)
class TubSemanticMediaWikiAdapterTest {

  private TubSemanticMediaWikiAdapter subject;

  @Mock private TubApiService tubApiService;
  @Mock private EntryConverter entryConverter;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new TubSemanticMediaWikiAdapter(tubApiService, entryConverter);
  }

  @Test
  void getEntriesShouldGetEntries() {
    final var title = TitlePrintoutsBuilder.builder().titleTransliterated("Title").build();
    final var printouts = TubPrintouts.builder().titles("Title", title).build();
    final var expected = List.of(TubEntry.builder().titleTransliterated("Title").build());
    Mockito.when(tubApiService.getData()).thenReturn(printouts);
    Mockito.when(entryConverter.convert(printouts)).thenReturn(expected);
    assertThat(subject.getEntries()).isEqualTo(expected);
  }
}
