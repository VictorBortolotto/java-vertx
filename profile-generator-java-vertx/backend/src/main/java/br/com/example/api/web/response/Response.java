package br.com.example.api.web.response;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Response {
  
  private RoutingContext routingContext;

  public Response(RoutingContext routingContext) {
    this.routingContext = routingContext;
  }

  public void send(int statusCode, JsonObject jsonObject) {
    routingContext.response()
      .putHeader("content-type", "application/json")
      .setStatusCode(statusCode)
      .end(jsonObject.toString());

  }

}
