package br.com.example.api.repository;


import br.com.example.api.model.Experience;
import io.vertx.core.Future;

public interface ExperienceRepository {
  
  Future<Experience> findByProfileId(long id);
  void save(Experience experience, long profileId);
  void delete(long idProfile, long idExperience);
}
