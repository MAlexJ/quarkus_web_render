package com.malexj.service;

import com.malexj.controller.task.dto.TaskResponse;
import com.malexj.controller.task.dto.TasksResponse;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class TaskService {

  private final CopyOnWriteArrayList<TaskResponse> tasks = new CopyOnWriteArrayList<>();

  public void addTask(TaskResponse task) {
    tasks.add(task);
  }

  public TasksResponse findTasks() {
    return new TasksResponse(tasks);
  }
}
