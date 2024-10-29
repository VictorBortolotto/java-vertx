package br.com.example.api.controller;

import br.com.example.api.model.Experience;
import br.com.example.api.services.serviceImpl.ExperienceServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class ExperienceController {
  private Vertx vertx;
  private Router router;

  public ExperienceController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    save();
    delete();
    update();
  }

  private void save() {
    router.post("/api/experience").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var experience = Experience.getExperienceFromJsonObject(bodyHandler.toJsonObject());
        new ExperienceServiceImpl(handle, vertx).save(experience);
      });
    });
  }

  private void delete() {
    router.delete("/api/experience").handler(handle -> {
      final var id = Long.valueOf(handle.request().params().get("id"));
      new ExperienceServiceImpl(handle, vertx).delete(id);
   });
  }

  private void update() {
    router.put("/api/experience").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var id = Long.valueOf(handle.request().params().get("id"));
        final var experience = Experience.getExperienceFromJsonObject(bodyHandler.toJsonObject());
        new ExperienceServiceImpl(handle, vertx).update(experience, id);
      });
    });
  }
}
