package br.com.example.api.controller;

import br.com.example.api.model.Candidate;
import br.com.example.api.service.CandidateService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class CandidateController {

  private Vertx vertx;
  private Router router;

  public CandidateController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    save();
  }

  private void save() {
    router.post("/api/new/candidate").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var candidate = Candidate.getCandidateFromJsonObject(bodyHandler.toJsonObject());
        new CandidateService(handle, vertx).save(candidate);
      });
    });
  }
}
