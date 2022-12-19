package org.tub.tubtextservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tub.tubtextservice.client.TodoClient;
import org.tub.tubtextservice.client.TubClient;
import org.tub.tubtextservice.model.todo.Todo;
import org.tub.tubtextservice.model.tubresponse.TubResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping()
public class TubTextController {

  TubClient tubClient;
  TodoClient todoClient;

  TubTextController(TubClient tubClient, TodoClient todoClient) {
    this.tubClient = tubClient;
    this.todoClient = todoClient;
  }

  @GetMapping("/word")
  public Mono<TubResponse> getWordFile() {
    return tubClient.queryTub(
        "ask",
        "json",
        "[[Category:Title]]|?Category|?Book%20type|?Has%20number%20of%20commentaries|?Title%20(Arabic)|?Title%20(transliterated)|?Has%20author(s)|?Has%20translator(s)|?Has%20a%20catalogue%20description|?Has%20base%20text|limit=3");
  }

  @GetMapping("todo")
  public Mono<List<Todo>> getTodos() {
    return todoClient.todos();
  }
}
