package br.com.example.api.services.service;

import java.util.List;

import br.com.example.api.model.Skill;
import io.vertx.core.Future;

public interface SkillService {
  Future<List<Skill>> findAllByProfileId(long profileId);
  void save(Skill candidate);
  void delete(long id);
}
