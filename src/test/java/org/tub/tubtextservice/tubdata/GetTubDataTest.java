package org.tub.tubtextservice.tubdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.builder.TitlePrintoutsBuilder;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.usecase.tub.convert.ConvertToEntry;
import org.tub.tubtextservice.usecase.tub.getdata.GetDataCommand;
import org.tub.tubtextservice.usecase.tub.getdata.GetTubData;
import org.tub.tubtextservice.usecase.tub.getdata.model.TubPrintouts;

@ExtendWith(MockitoExtension.class)
class GetTubDataTest {

  private GetTubData subject;

  @Mock private GetDataCommand getDataCommand;
  @Mock private ConvertToEntry convertToEntry;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new GetTubData(getDataCommand, convertToEntry);
  }

  @Test
  void getEntriesShouldGetEntries() {
    final var title = TitlePrintoutsBuilder.builder().titleTransliterated("Title").build();
    final var printouts = TubPrintouts.builder().titles("Title", title).build();
    final var expected = List.of(TubEntry.builder().titleTransliterated("Title").build());
    Mockito.when(getDataCommand.getData()).thenReturn(printouts);
    Mockito.when(convertToEntry.convert(printouts)).thenReturn(expected);
    assertThat(subject.getEntries()).isEqualTo(expected);
  }
}
