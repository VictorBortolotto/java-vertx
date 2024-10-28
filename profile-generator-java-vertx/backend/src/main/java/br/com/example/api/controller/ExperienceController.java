package br.com.example.api.controller;

import br.com.example.api.web.response.Response;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class ExperienceController {
  private Router router;

  public ExperienceController(Router router) {
    this.router = router;
  }

  public void init() {
    save();
  }

  private void save() {
    router.get("/api/new/candidate").handler(handle -> {
      new Response(handle).send(200, new JsonObject().put("message", "Tá ok!"));
    });
  }
}
