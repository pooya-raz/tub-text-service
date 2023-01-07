package org.tub.tubtextservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.client.TubClient;
import org.tub.tubtextservice.model.tubresponse.TubResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping()
public class TubTextController {

  TubClient tubClient;

  @GetMapping("/titles")
  public Mono<TubResponse> getWordFile() {
    return tubClient.queryTub(
        "ask",
        "json",
        "[[Category:Title]]|?Category|?Book%20type|?Has%20number%20of%20commentaries|?Title%20(Arabic)|?Title%20(transliterated)|?Has%20author(s)|?Has%20translator(s)|?Has%20a%20catalogue%20description|?Has%20base%20text|limit=3");
  }

  @GetMapping("/authors")
  public Mono<TubResponse> getAuthors() {
    return tubClient.queryTub(
        "ask",
        "json",
        "[[Category:Author]]|?Full%20name%20(transliterated)|?Death%20(Hijri)|?Death%20(Gregorian)|?Death%20(Shamsi)|?Death%20(Hijri)%20text|?Death%20(Gregorian)%20text|?Death%20(Shamsi)%20text|limit=3");
  }
}
