package br.com.example.my_first_verticle_api.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RouterContext {
  
  private Router router;

  public RouterContext(Vertx vertx) {
    this.router = Router.router(vertx);
  }

  public Router getRouter() {
    return router;
  }

  public void setRouter(Router router) {
    this.router = router;
  }  

}
