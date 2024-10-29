package br.com.example.api.services.service;


import java.util.List;

import br.com.example.api.model.Experience;
import io.vertx.core.Future;

public interface ExperienceService {
  Future<List<Experience>> findAllByProfileId(long profileId);
  void save(Experience experience);
  void delete(long id);
  void update(Experience experience, long id);
}
