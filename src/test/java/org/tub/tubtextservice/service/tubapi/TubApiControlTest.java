package org.tub.tubtextservice.service.tubapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tub.tubtextservice.service.tubapi.service.TubApiService;

@ExtendWith(MockitoExtension.class)
class TubApiControlTest {

  private TubApiControl subject;

  @Mock private TubApiService tubApiService;

  @BeforeEach
  void setUpBeforeEach() {
    subject = new TubApiControl(tubApiService);
  }
}
