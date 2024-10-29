package br.com.example.api.services.service;

import java.util.List;

import br.com.example.api.model.Link;
import io.vertx.core.Future;

public interface LinkService {
  Future<List<Link>> findAllByProfileId(long profileId);
  void save(Link candidate);
  void delete(long id);
  void update(Link link, long id);
}
