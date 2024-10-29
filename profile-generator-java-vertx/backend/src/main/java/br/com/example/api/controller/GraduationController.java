package br.com.example.api.controller;

import br.com.example.api.model.Graduation;
import br.com.example.api.services.serviceImpl.GraduationServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class GraduationController {
  
  private Vertx vertx;
  private Router router;

  public GraduationController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    save();
    delete();
    update();
  }

  private void save() {
    router.post("/api/graduation").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var graduation = Graduation.getGraduationFromJsonObject(bodyHandler.toJsonObject());
        new GraduationServiceImpl(handle, vertx).save(graduation);
      });
    });
  }

  private void delete() {
    router.delete("/api/graduation").handler(handle -> {
      final var id = Long.valueOf(handle.request().params().get("id"));
      new GraduationServiceImpl(handle, vertx).delete(id);
   });
  }

  private void update() {
    router.put("/api/graduation").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var id = Long.valueOf(handle.request().params().get("id"));
        final var graduation = Graduation.getGraduationFromJsonObject(bodyHandler.toJsonObject());
        new GraduationServiceImpl(handle, vertx).update(graduation, id);
      });
    });
  }
}
