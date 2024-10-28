package br.com.example.api.repository;

import br.com.example.api.model.User;
import io.vertx.core.Future;

public interface UserRepository {

  void save(User user);

  Future<User> findByEmail(String email);
} 
