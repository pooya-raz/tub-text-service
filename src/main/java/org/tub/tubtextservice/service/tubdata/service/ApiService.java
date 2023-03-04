package org.tub.tubtextservice.service.tubdata.service;

import org.tub.tubtextservice.service.tubdata.model.ApiData;

/** A service that retrieves data from an API. */
public interface ApiService {

  /**
   * Retrieves data from an API.
   *
   * @return data from an API
   */
  ApiData getData();
}
