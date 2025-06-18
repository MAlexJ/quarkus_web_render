package com.malexj.task;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TaskService {

  private final TaskRepository repository;

  @Inject
  public TaskService(TaskRepository repository) {
    this.repository = repository;
  }

  public TaskResponse findTasks() {
    var panacheQueryTasks = repository.findAll();
    return new TaskResponse(panacheQueryTasks.list());
  }
}
