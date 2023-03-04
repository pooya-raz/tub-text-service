package org.tub.tubtextservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.model.property.TubProperties;
import org.tub.tubtextservice.service.tubdata.client.TubClient;
import org.tub.tubtextservice.service.tubdata.model.tubresponse.TubResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping()
public class TubTextController {

  public static final String ACTION_ASK = "ask";
  public static final String FORMAT_JSON = "json";
  private final TubClient tubClient;

  private final TubProperties tubProperties;

  public TubTextController(TubClient tubClient, TubProperties tubProperties) {
    this.tubClient = tubClient;
    this.tubProperties = tubProperties;
  }

  @GetMapping("/titles")
  public Mono<TubResponse> getWordFile() {
    return tubClient.queryTub(ACTION_ASK, FORMAT_JSON, tubProperties.query().titles());
  }

  @GetMapping("/authors")
  public Mono<TubResponse> getAuthors() {
    return tubClient.queryTub(ACTION_ASK, FORMAT_JSON, tubProperties.query().authors());
  }
}
