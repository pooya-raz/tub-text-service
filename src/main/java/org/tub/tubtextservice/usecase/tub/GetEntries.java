package org.tub.tubtextservice.usecase.tub;

import java.util.List;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.usecase.tub.port.GetEntriesPort;

public class GetEntries {
    private final GetEntriesPort getEntriesPort;

    public GetEntries(GetEntriesPort getEntriesPort) {
        this.getEntriesPort = getEntriesPort;
    }
  List<TubEntry> getEntries() {
        return getEntriesPort.getEntries();
    }
}
