package br.com.example.api;

import java.util.Set;

import br.com.example.api.authentication.auth.Authentication;
import br.com.example.api.controller.CandidateController;
import br.com.example.api.controller.ExperienceController;
import br.com.example.api.controller.GraduationController;
import br.com.example.api.controller.LenguageController;
import br.com.example.api.controller.LinkController;
import br.com.example.api.controller.ProfileController;
import br.com.example.api.controller.SkillController;
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
      .handler(CorsHandler.create()
        .addOrigin("http://localhost:3000/*")
        .allowedHeaders(configureAllowedHeaders())
        .allowedMethods(configureAllowedMethods()))
      .handler(new Authentication(vertx).authenticate());
  
    new UserController(router, vertx).init();
    new CandidateController(router, vertx).init();
    new ExperienceController(router, vertx).init();
    new GraduationController(router, vertx).init();
    new LinkController(router, vertx).init();
    new LenguageController(router, vertx).init();
    new SkillController(router, vertx).init();
    new ProfileController(router, vertx).init();

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
