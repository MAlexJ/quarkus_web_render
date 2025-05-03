package com.malexj.controller.ping;

import com.malexj.controller.ping.dto.PingResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/ping")
public class PingRestController {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<PingResponse> getPingPong() {
    return RestResponse.ResponseBuilder.ok(new PingResponse()).build();
  }

  @HEAD
  @Produces(MediaType.TEXT_PLAIN)
  public RestResponse<Void> headPingPong() {
    return RestResponse.ok();
  }
}
