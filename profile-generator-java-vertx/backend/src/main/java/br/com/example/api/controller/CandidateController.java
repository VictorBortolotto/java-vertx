package br.com.example.api.controller;

import br.com.example.api.model.Candidate;
import br.com.example.api.services.serviceImpl.CandidateServiceImpl;
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
    update();
    findByUserId();
  }

  private void save() {
    router.post("/api/candidate").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var candidate = Candidate.getCandidateFromJsonObject(bodyHandler.toJsonObject());
        new CandidateServiceImpl(handle, vertx).save(candidate);
      });
    });
  }

  private void update() {
    router.put("/api/candidate").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var id = Long.valueOf(handle.request().params().get("id"));
        final var candidate = Candidate.getCandidateFromJsonObject(bodyHandler.toJsonObject());
        new CandidateServiceImpl(handle, vertx).update(candidate, id);
      });
    });
  }

  private void findByUserId() {
    router.get("/api/candidate").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var id = Long.valueOf(handle.request().params().get("id"));
        new CandidateServiceImpl(handle, vertx).findByUserId(id);
      });
    });
  }
}
