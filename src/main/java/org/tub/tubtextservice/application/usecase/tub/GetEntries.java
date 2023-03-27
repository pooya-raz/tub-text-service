package org.tub.tubtextservice.application.usecase.tub;

import java.util.List;
import org.tub.tubtextservice.application.usecase.tub.port.GetEntriesPort;
import org.tub.tubtextservice.domain.TubEntry;

public class GetEntries {
    private final GetEntriesPort getEntriesPort;

    public GetEntries(GetEntriesPort getEntriesPort) {
        this.getEntriesPort = getEntriesPort;
    }
  List<TubEntry> getEntries() {
        return getEntriesPort.getEntries();
    }
}
