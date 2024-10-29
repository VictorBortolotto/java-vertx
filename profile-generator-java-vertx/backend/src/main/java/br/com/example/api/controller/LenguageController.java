package br.com.example.api.controller;

import br.com.example.api.model.Lenguage;
import br.com.example.api.services.serviceImpl.LenguageServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class LenguageController {
  private Vertx vertx;
  private Router router;

  public LenguageController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    save();
    delete();
    update();
  }

  private void save() {
    router.post("/api/lenguage").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var lenguage = Lenguage.getLenguageFromJsonObject(bodyHandler.toJsonObject());
        new LenguageServiceImpl(handle, vertx).save(lenguage);
      });
    });
  }

  private void delete() {
    router.delete("/api/lenguage").handler(handle -> {
      final var id = Long.valueOf(handle.request().params().get("id"));
      new LenguageServiceImpl(handle, vertx).delete(id);
   });
  }

  private void update() {
    router.put("/api/lenguage").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var id = Long.valueOf(handle.request().params().get("id"));
        final var lenguage = Lenguage.getLenguageFromJsonObject(bodyHandler.toJsonObject());
        new LenguageServiceImpl(handle, vertx).update(lenguage, id);
      });
    });
  }
}
