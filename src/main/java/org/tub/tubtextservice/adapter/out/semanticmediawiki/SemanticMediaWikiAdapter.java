package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import org.springframework.stereotype.Service;
import org.tub.tubtextservice.application.usecase.createdocx.dto.in.EntriesDto;
import org.tub.tubtextservice.application.usecase.createdocx.port.GetEntriesPort;
import org.tub.tubtextservice.domain.TubEntry;

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
    public EntriesDto getEntries() {
    var printouts = getPrintouts.get();
    final var entries = convertToEntry.convert(printouts);
    return new EntriesDto(entries);
    }
}