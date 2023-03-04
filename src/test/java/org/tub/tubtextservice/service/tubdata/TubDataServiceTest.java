package org.tub.tubtextservice.service.tubdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.service.tubdata.converter.EntryConverter;
import org.tub.tubtextservice.service.tubdata.service.TubApiService;

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
  void getPrintouts() {
    final var x = false;
    assertThat(x).isTrue();
  }
}
