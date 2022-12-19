package org.tub.tubtextservice.model.tubresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TypeId {
  @JsonProperty("_txt")
  TXT,
  @JsonProperty("_wpg")
  WPG
}
