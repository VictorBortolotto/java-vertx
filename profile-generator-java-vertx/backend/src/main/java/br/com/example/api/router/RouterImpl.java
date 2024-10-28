package br.com.example.api.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RouterImpl {
  
  private Router router;

  public RouterImpl(Vertx vertx) {
    this.router = Router.router(vertx);
  }

  public Router getRouter() {
    return router;
  }

  public void setRouter(Router router) {
    this.router = router;
  }
}
