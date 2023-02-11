package org.tub.tubtextservice.control;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.service.tubapi.model.TubData;
import org.tub.tubtextservice.service.tubapi.TubApiService;
import org.tub.tubtextservice.service.word.TubWordService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TubWordControlTest {
    private TubWordControl subject;
    @Mock
    private TubApiService tubApiService;

    @Mock
    private TubWordService tubWordService;

    @BeforeEach
    void setUpBeforeEach() {
        subject = new TubWordControl(tubApiService, tubWordService);
    }

  @Test
  void shouldCreateAWordFile() {
      final var tubData = new TubData(null,null,null,null);
      when(tubApiService.getData()).thenReturn(tubData);
      when(tubWordService.createWordDocument(tubData)).thenReturn(new XWPFDocument());
    final var actual = subject.createWordDocumentFromTub();
    System.out.println(actual.getDocument());
    assertThat(actual.getDocument()).isNotNull();
        }

}