package org.tub.tubtextservice.adapter.semantic;

import java.util.List;
import org.springframework.stereotype.Service;
import org.tub.tubtextservice.adapter.semantic.converter.EntryConverter;
import org.tub.tubtextservice.adapter.semantic.service.TubApiService;
import org.tub.tubtextservice.domain.model.tubentry.TubEntry;

/** This class is responsible for the workflow of retrieving data from the TUB API. */
@Service
public class TubSemanticMediaWikiAdapter {

    private final TubApiService tubApiService;
    private final EntryConverter entryConverter;

  public TubSemanticMediaWikiAdapter(TubApiService tubApiService, EntryConverter entryConverter) {
        this.tubApiService = tubApiService;
        this.entryConverter = entryConverter;
    }

    /**
     * The main workflow to retrieve the data from the TUB API and convert it to a list of {@link
     * TubEntry}.
     *
     * @return A list of {@link TubEntry}
     */
    public List<TubEntry> getEntries() {
        var printouts = tubApiService.getData();
        return entryConverter.convert(printouts);
    }
}