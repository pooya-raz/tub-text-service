package org.tub.tubtextservice.service.tubapi;

import org.tub.tubtextservice.model.domain.Entry;
import org.tub.tubtextservice.service.tubapi.converter.EntryConverter;
import org.tub.tubtextservice.service.tubapi.service.TubApiService;

import java.util.List;

/** This class is responsible for the workflow of retrieving data from the TUB API. */
public class TubApiControl {

  private final TubApiService tubApiService;
  private final EntryConverter entryConverter;

  public TubApiControl(TubApiService tubApiService, EntryConverter entryConverter) {
    this.tubApiService = tubApiService;
    this.entryConverter = entryConverter;
  }

  /**
   * The main workflow to retrieve the data from the TUB API and convert it to a list of {@link
   * Entry}.
   *
   * @return A list of {@link Entry}
   */
  public List<Entry> getPrintouts() {
    var printouts = tubApiService.getData();
    return entryConverter.convert(printouts);
  }
}
