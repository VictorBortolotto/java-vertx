package br.com.example.api.controller;

import br.com.example.api.model.User;
import br.com.example.api.services.serviceImpl.UserServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class UserController {
  
  private Vertx vertx;
  private Router router;

  public UserController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    save();
    login();
  }

  private void save() {
    router.post("/auth/signup").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var jsonObject = bodyHandler.toJsonObject();
        final var user = User.getUserFromJsonObject(jsonObject);
        new UserServiceImpl(handle, vertx).save(user);
      });
    });
  }

  private void login() {
    router.post("/auth/login").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var jsonObject = bodyHandler.toJsonObject();
        final var user = User.getUserFromJsonObject(jsonObject);
        new UserServiceImpl(handle, vertx).login(user);
      });
    });
  }

}
