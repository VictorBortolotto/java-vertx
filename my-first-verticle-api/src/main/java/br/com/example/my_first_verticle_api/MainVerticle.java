package br.com.example.my_first_verticle_api;

import br.com.example.my_first_verticle_api.controller.HelloWorldController;
import br.com.example.my_first_verticle_api.router.RouterContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = new RouterContext(vertx).getRouter();
    
    new HelloWorldController().builder(router);

    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
