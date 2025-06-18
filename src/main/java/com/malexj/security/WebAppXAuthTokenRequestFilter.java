package com.malexj.security;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class WebAppXAuthTokenRequestFilter {

  private static final Logger LOG = Logger.getLogger(WebAppXAuthTokenRequestFilter.class);

  public static final String X_AUTH_TOKEN_HEADER = "X-Auth-Token";
  public static final String WEB_APP_USER_ATTRIBUTE_KEY = "webAppUser";
  public static final String WEB_APP_USER_ID_ATTRIBUTE_KEY = "userId";

  private final WebAppInitDataService service;

  private final String apiPath;

  @Inject
  public WebAppXAuthTokenRequestFilter(
      @ConfigProperty(name = "quarkus.http.root-path", defaultValue = "/api") String rootPath,
      WebAppInitDataService webAppInitDataService) {
    this.service = webAppInitDataService;
    this.apiPath = rootPath;
  }

  @ServerRequestFilter(priority = 1, preMatching = true)
  public Optional<Response> authHeaderCheck(ContainerRequestContext ctx) {

    if(true){
      return Optional.empty();
    }

    var uri = ctx.getUriInfo().getAbsolutePath();
    if (!uri.getPath().contains(apiPath)) { // Only apply filter to /api/** paths
      return Optional.empty(); // Skip auth for non-API routes
    }

    /*
     * todo: refactor method
     */
    var thInitDataOpt =
        // Extract the Authorization header
        Optional.ofNullable(ctx.getHeaderString(X_AUTH_TOKEN_HEADER))
            .filter(header -> !header.isBlank());

    if (thInitDataOpt.isEmpty()) {
      return Optional.of(
          Response.status(Response.Status.UNAUTHORIZED)
              .entity("The X-Auth-Token header is missing or empty in the request")
              .build());
    }

    LOG.debug("%s: %s".formatted(X_AUTH_TOKEN_HEADER, thInitDataOpt));

    Optional<WebAppUser> webAppUserOpt = service.validateTgInitData(thInitDataOpt.get());

    if (webAppUserOpt.isEmpty()) {
      return Optional.of(
          Response.status(Response.Status.UNAUTHORIZED)
              .entity("The X-Auth-Token header contains invalid `tgInitData` data")
              .build());
    }

    WebAppUser webAppUser = webAppUserOpt.get();

    // Set attributes into request context
    ctx.setProperty(WEB_APP_USER_ATTRIBUTE_KEY, webAppUser);
    ctx.setProperty(WEB_APP_USER_ID_ATTRIBUTE_KEY, webAppUser.id());

    // If everything is good, continue with the request
    return Optional.empty();
  }
}
