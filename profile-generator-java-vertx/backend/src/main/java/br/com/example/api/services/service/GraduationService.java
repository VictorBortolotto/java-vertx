package br.com.example.api.services.service;

import java.util.List;

import br.com.example.api.model.Graduation;
import io.vertx.core.Future;

public interface GraduationService {
  void save(Graduation graduation);
  Future<List<Graduation>> findByProfileId(long profileId);
  void delete(long id);
  void update(Graduation graduation, long id);
}
