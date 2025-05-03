package com.malexj.controller.task;

import com.malexj.controller.task.dto.TasksResponse;
import com.malexj.service.TaskService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/tasks")
public class TaskRestController {

  private final TaskService service;

  @Inject
  public TaskRestController(TaskService service) {
    this.service = service;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public RestResponse<TasksResponse> headPingPong() {
    return RestResponse.ok(service.findTasks());
  }
}
