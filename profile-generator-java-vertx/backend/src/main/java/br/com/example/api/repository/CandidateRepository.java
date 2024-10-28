package br.com.example.api.repository;

import br.com.example.api.model.Candidate;
import io.vertx.core.Future;

public interface CandidateRepository {
  
  Future<Candidate> findById(long id);
  void save(Candidate candidate);
  void update(Candidate candidate, long id);

}
