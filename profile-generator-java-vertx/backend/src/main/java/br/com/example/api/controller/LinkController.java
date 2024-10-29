package br.com.example.api.controller;

import br.com.example.api.model.Link;
import br.com.example.api.services.serviceImpl.LinkServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class LinkController {
  private Vertx vertx;
  private Router router;

  public LinkController(Router router, Vertx vertx) {
    this.vertx = vertx;
    this.router = router;
  }

  public void init() {
    save();
    delete();
    update();
  }

  private void save() {
    router.post("/api/link").handler(handle -> {
      handle.request().bodyHandler(bodyHandler -> {
        final var link = Link.getLinkFromJsonObject(bodyHandler.toJsonObject());
        new LinkServiceImpl(handle, vertx).save(link);
      });
    });
  }

  private void delete() {
    router.delete("/api/link").handler(handle -> {
      final var id = Long.valueOf(handle.request().params().get("id"));
      new LinkServiceImpl(handle, vertx).delete(id);
   });
  }

   private void update() {
    router.put("/api/link").handler(handle -> {
       handle.request().bodyHandler(bodyHandler -> {
        final var id = Long.valueOf(handle.request().params().get("id"));
        final var link = Link.getLinkFromJsonObject(bodyHandler.toJsonObject());
        new LinkServiceImpl(handle, vertx).update(link,id);
      });
    });
  }
}
