package br.com.example.api;

import java.util.Set;

import br.com.example.api.authentication.auth.Authentication;
import br.com.example.api.controller.CandidateController;
import br.com.example.api.controller.UserController;
import br.com.example.api.router.RouterImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.CorsHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    final var router = new RouterImpl(vertx).getRouter();
    router.route("/api/*")
      .handler(new Authentication(vertx).authenticate())
      .handler(CorsHandler.create()
      .addOrigin("http://localhost:3000/*")
      .allowedHeaders(configureAllowedHeaders())
      .allowedMethods(configureAllowedMethods()));
  
    new UserController(router, vertx).init();
    new CandidateController(router, vertx).init();

    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  private Set<HttpMethod> configureAllowedMethods() {
    return Set.of(HttpMethod.DELETE,
      HttpMethod.POST,
      HttpMethod.GET,
      HttpMethod.PATCH,
      HttpMethod.UPDATE);
  }

  private Set<String> configureAllowedHeaders() {
    return Set.of("x-requested-with",
      "Access-Control-Allow-Origin",
      "Access-Control-Allow-Credentials",
      "Content-Type",
      "Authorization");
  }
  

}
