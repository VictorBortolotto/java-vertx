package br.com.example.api.authentication.auth;


import org.mindrot.jbcrypt.BCrypt;

import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.handler.JWTAuthHandler;

public class Authentication {
  
  private Vertx vertx;

  public Authentication(Vertx vertx) {
    this.vertx = vertx;
  }

  public JWTAuthHandler authenticate() {
    return JWTAuthHandler.create(JWTAuth.create(vertx, configureJWT()));
  }

  private JWTAuthOptions configureJWT() {
    return new JWTAuthOptions().addPubSecKey(new PubSecKeyOptions().setAlgorithm("HS256").setBuffer("my-secret"));
  }

  public static String generateHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean verifyPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }

}
