package com.malexj.task;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Objects;

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

  public void createTask(TaskRequest request) {
    var task = new Task();
    task.setName(Objects.requireNonNull(request.name(), "Name is required"));
    task.setDescription(Objects.requireNonNull(request.description(), "Description is required"));
    repository.persist(task);
  }
}
