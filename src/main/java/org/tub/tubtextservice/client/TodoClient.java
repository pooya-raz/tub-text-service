package org.tub.tubtextservice.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.tub.tubtextservice.model.todo.Todo;
import reactor.core.publisher.Mono;

import java.util.List;

@HttpExchange("/todos")
public interface TodoClient {
  @GetExchange
  Mono<List<Todo>> todos();
}
