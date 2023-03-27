package org.tub.tubtextservice.application.usecase.docx.port;

import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;

public interface GetEntriesPort {
  EntriesDto getEntries();
}
