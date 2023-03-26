package org.tub.tubtextservice.adapter.semantic.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;

public class Results {

  private final Map<String, Data> dataMap = new HashMap<>();

  public Map<String, Data> getDataMap() {
    return dataMap;
  }

  @JsonAnySetter
  public void setDataMap(String key, Data data) {
    this.dataMap.put(key, data);
  }
}
