package org.tub.tubtextservice.adapter.semantic.service;

import org.tub.tubtextservice.adapter.semantic.model.ApiData;

/** A service that retrieves data from an API. */
public interface ApiService {

  /**
   * Retrieves data from an API.
   *
   * @return data from an API
   */
  ApiData getData();
}
