package br.com.example.api.controller;

import br.com.example.api.services.serviceImpl.ProfileServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class ProfileController {
  
  private Vertx vertx;
  private Router router;

  public ProfileController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    getProfile();
  }

  private void getProfile() {
    router.get("/api/profile").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var profileId = Long.valueOf(handle.request().params().get("profileId"));
        final var candidateId = Long.valueOf(handle.request().params().get("candidateId"));
        new ProfileServiceImpl(handle, vertx).findProfile(profileId, candidateId);
      });
    });
  }
}
