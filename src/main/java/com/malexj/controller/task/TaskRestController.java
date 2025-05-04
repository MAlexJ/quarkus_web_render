package com.malexj.controller.task;

import static com.malexj.filter.XAuthTokenAuthorizationRequestFilter.WEB_APP_USER_ATTRIBUTE_KEY;
import static com.malexj.filter.XAuthTokenAuthorizationRequestFilter.WEB_APP_USER_ID_ATTRIBUTE_KEY;

import com.malexj.controller.task.dto.TasksResponse;
import com.malexj.service.TaskService;
import com.malexj.telegram.dto.WebAppUser;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/tasks")
public class TaskRestController {

  private static final Logger LOG = Logger.getLogger(TaskRestController.class);


  private final TaskService service;

  @Inject
  public TaskRestController(TaskService service) {
    this.service = service;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public RestResponse<TasksResponse> headPingPong(@Context ContainerRequestContext context) {

    var user = (WebAppUser) context.getProperty(WEB_APP_USER_ATTRIBUTE_KEY);
    var userId = (Long) context.getProperty(WEB_APP_USER_ID_ATTRIBUTE_KEY);
    LOG.info("User %s (%s) requested tasks".formatted(userId, user));

    return RestResponse.ok(service.findTasks());
  }
}
