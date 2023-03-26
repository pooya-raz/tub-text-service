package org.tub.tubtextservice.usecase.tub.getdata;

import java.util.List;
import org.springframework.stereotype.Service;
import org.tub.tubtextservice.domain.TubEntry;
import org.tub.tubtextservice.usecase.tub.convert.ConvertToEntry;

/** This class is responsible for the workflow of retrieving data from the TUB API. */
@Service
public class GetTubData {

    private final GetDataCommand getDataCommand;
    private final ConvertToEntry convertToEntry;

  public GetTubData(GetDataCommand getDataCommand, ConvertToEntry convertToEntry) {
        this.getDataCommand = getDataCommand;
        this.convertToEntry = convertToEntry;
    }

    /**
     * The main workflow to retrieve the data from the TUB API and convert it to a list of {@link
     * TubEntry}.
     *
     * @return A list of {@link TubEntry}
     */
    public List<TubEntry> getEntries() {
        var printouts = getDataCommand.getData();
        return convertToEntry.convert(printouts);
    }
}