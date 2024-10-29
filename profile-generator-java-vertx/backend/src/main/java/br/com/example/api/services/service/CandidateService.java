package br.com.example.api.services.service;

import br.com.example.api.model.Candidate;
import io.vertx.core.Future;

public interface CandidateService {
  Future<Candidate> findById(long id);
  Future<Boolean> findByUserId(long id);
  void save(Candidate candidate);
  void update(Candidate candidate, long id);
}
