package org.tub.tubtextservice.application.usecase.tub.port;

import java.util.List;
import org.tub.tubtextservice.domain.TubEntry;

public interface GetEntriesPort {
  List<TubEntry> getEntries();
}
