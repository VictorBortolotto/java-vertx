package br.com.example.my_first_verticle_api.controller;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class HelloWorldController {
  
  public void builder(Router router) {
    helloWorld(router);
    sendHelloWorldHTML(router);
  }

  //Send a JSON in body
  private void helloWorld(Router router) {
    router.get("/example/hello/1").handler(handle -> {
      JsonObject jsonObject = new JsonObject();
      jsonObject.put("hello", "Hello World");
      handle.json(jsonObject);
    });
  }

  //Send a HTML in body
  private void sendHelloWorldHTML(Router router) {
    router.get("/example/hello/2").handler(handle -> {
      HttpServerResponse response = handle.response().putHeader("content-type", "text/html; charset=utf-8");
      response.end("<h1>Hello World</h1>");
    });
  }

}
