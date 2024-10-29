package br.com.example.api.services.service;

import br.com.example.api.model.User;
import io.vertx.core.Future;

public interface UserService {
  void save(User user);
  Future<User> findByEmail(String email);
} 
