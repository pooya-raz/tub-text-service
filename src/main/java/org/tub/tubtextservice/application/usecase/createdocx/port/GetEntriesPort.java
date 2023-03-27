package org.tub.tubtextservice.application.usecase.createdocx.port;

import org.tub.tubtextservice.application.usecase.createdocx.dto.in.EntriesDto;

public interface GetEntriesPort {
  EntriesDto getEntries();
}
