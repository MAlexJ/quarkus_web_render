package com.malexj.task;

import static com.malexj.security.WebAppXAuthTokenRequestFilter.WEB_APP_USER_ATTRIBUTE_KEY;
import static com.malexj.security.WebAppXAuthTokenRequestFilter.WEB_APP_USER_ID_ATTRIBUTE_KEY;

import com.malexj.security.WebAppUser;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/api/v1/tasks")
public class TaskResource {

  private static final Logger LOG = Logger.getLogger(TaskResource.class);

  private final TaskService service;

  @Inject
  public TaskResource(TaskService service) {
    this.service = service;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<TaskResponse> findUserTasks(@Context ContainerRequestContext context) {

    var user = (WebAppUser) context.getProperty(WEB_APP_USER_ATTRIBUTE_KEY);
    var userId = (Long) context.getProperty(WEB_APP_USER_ID_ATTRIBUTE_KEY);
    LOG.info("User %s (%s) requested tasks".formatted(userId, user));

    return RestResponse.ok(service.findTasks());
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResponse<Void> createTask(
      @Context ContainerRequestContext context, TaskRequest request) {

    var user = (WebAppUser) context.getProperty(WEB_APP_USER_ATTRIBUTE_KEY);
    var userId = (Long) context.getProperty(WEB_APP_USER_ID_ATTRIBUTE_KEY);
    LOG.info("User %s (%s) requested create task".formatted(userId, user));

    service.createTask(request);

    return RestResponse.noContent();
  }
}
