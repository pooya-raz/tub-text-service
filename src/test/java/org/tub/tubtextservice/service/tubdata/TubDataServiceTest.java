package org.tub.tubtextservice.service.tubdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.service.tubdata.converter.EntryConverter;
import org.tub.tubtextservice.service.tubdata.model.TubPrintouts;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.printouts.TitlePrintouts;
import org.tub.tubtextservice.service.tubdata.service.TubApiService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TubDataServiceTest {

  private TubDataService subject;

  @Mock private TubApiService tubApiService;
  @Mock private EntryConverter entryConverter;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new TubDataService(tubApiService, entryConverter);
  }

  @Test
  void getEntriesShouldGetEntries() {
    final var title = TitlePrintouts.builder().titleTransliterated("Title").build();
    final var printouts = TubPrintouts.builder().titles("Title", title).build();
    final var expected = List.of(Entry.builder().titleTransliterated("Title").build());
    Mockito.when(tubApiService.getData()).thenReturn(printouts);
    Mockito.when(entryConverter.convert(printouts)).thenReturn(expected);
    assertThat(subject.getEntries()).isEqualTo(expected);
  }
}
