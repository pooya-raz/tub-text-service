package org.tub.tubtextservice.application.usecase.docx.port;

import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;

/**
 * Port for getting all entries in TUB.
 */
public interface GetEntriesPort {
  /**
   * Get all entries in TUB.
   *
   * @return all entries in TUB
   */
  EntriesDto getEntries();
}
