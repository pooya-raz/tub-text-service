package org.tub.tubtextservice.adapter.semanticmediawiki;

import java.util.List;
import org.springframework.stereotype.Service;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.usecase.tub.port.GetEntriesPort;

/** This class is responsible for the workflow of retrieving data from the TUB API. */
@Service
public class SemanticMediaWikiAdapter implements GetEntriesPort {

  private final GetPrintouts getPrintouts;
    private final ConvertToEntry convertToEntry;

  public SemanticMediaWikiAdapter(GetPrintouts getPrintouts, ConvertToEntry convertToEntry) {
    this.getPrintouts = getPrintouts;
        this.convertToEntry = convertToEntry;
    }

    /**
     * The main workflow to retrieve the data from the TUB API and convert it to a list of {@link
     * TubEntry}.
     *
     * @return A list of {@link TubEntry}
     */
    public List<TubEntry> getEntries() {
    var printouts = getPrintouts.get();
        return convertToEntry.convert(printouts);
    }
}