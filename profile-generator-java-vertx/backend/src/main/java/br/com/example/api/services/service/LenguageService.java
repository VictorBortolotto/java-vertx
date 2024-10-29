package br.com.example.api.services.service;

import java.util.List;

import br.com.example.api.model.Lenguage;
import io.vertx.core.Future;

public interface LenguageService {
  Future<List<Lenguage>> findAllByProfileId(long profileId);
  void save(Lenguage lenguage);
  void delete(long id);
  void update(Lenguage lenguage, long id);
}
