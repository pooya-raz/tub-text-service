package org.tub.tubtextservice.adapter.out.semanticmediawiki;

import org.springframework.stereotype.Service;
import org.tub.tubtextservice.application.usecase.docx.dto.in.EntriesDto;
import org.tub.tubtextservice.application.usecase.docx.port.GetEntriesPort;
import org.tub.tubtextservice.domain.TubEntry;

/** This class is responsible for the workflow of retrieving data from the TUB API. */
@Service
public class SemanticMediaWikiAdapter implements GetEntriesPort {

  private final TubDataFetcher tubDataFetcher;
  private final EntryConverter entryConverter;

  public SemanticMediaWikiAdapter(TubDataFetcher tubDataFetcher, EntryConverter entryConverter) {
    this.tubDataFetcher = tubDataFetcher;
    this.entryConverter = entryConverter;
    }

    /**
     * The main workflow to retrieve the data from the TUB API and convert it to a list of {@link
     * TubEntry}.
     *
     * @return A list of {@link TubEntry}
     */
    public EntriesDto getEntries() {
    var printouts = tubDataFetcher.get();
    final var entries =entryConverter.convert(printouts);
    final var comp = new EntryComparator();
    entries.sort(comp);
    return new EntriesDto(entries);
    }
}

