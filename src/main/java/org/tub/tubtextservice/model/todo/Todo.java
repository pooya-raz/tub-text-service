package org.tub.tubtextservice.model.todo;

public record Todo(Long id, String title, boolean completed, Long userId) {}
