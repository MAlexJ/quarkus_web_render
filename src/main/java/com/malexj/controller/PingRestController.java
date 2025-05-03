package com.malexj.controller;

import com.malexj.controller.dto.PingResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/api/v1/ping")
public class PingRestController {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<PingResponse> pong() {
    return RestResponse.ResponseBuilder.ok(new PingResponse()).build();
  }
}
