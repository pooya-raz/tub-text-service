package org.tub.tubtextservice.domain.tubdata;

import java.util.List;
import org.springframework.stereotype.Service;
import org.tub.tubtextservice.domain.model.tubentry.TubEntry;
import org.tub.tubtextservice.domain.tubdata.converter.EntryConverter;
import org.tub.tubtextservice.domain.tubdata.service.TubApiService;

/** This class is responsible for the workflow of retrieving data from the TUB API. */
@Service
public class TubDataService {

    private final TubApiService tubApiService;
    private final EntryConverter entryConverter;

    public TubDataService(TubApiService tubApiService, EntryConverter entryConverter) {
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