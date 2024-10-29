package br.com.example.api.controller;

import br.com.example.api.model.Skill;
import br.com.example.api.services.serviceImpl.SkillServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SkillController {
  private Vertx vertx;
  private Router router;

  public SkillController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    save();
    delete();
  }

  private void save() {
    router.post("/api/skill").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var skill = Skill.getSkillFromJsonObject(bodyHandler.toJsonObject());
        new SkillServiceImpl(handle, vertx).save(skill);
      });
    });
  }

  private void delete() {
    router.delete("/api/skill").handler(handle -> {
      final var id = Long.valueOf(handle.request().params().get("id"));
      new SkillServiceImpl(handle, vertx).delete(id);
   });
  }
}
