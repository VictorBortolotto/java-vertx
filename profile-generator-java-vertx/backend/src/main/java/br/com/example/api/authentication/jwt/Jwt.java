package br.com.example.api.authentication.jwt;

import br.com.example.api.model.User;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class Jwt {
  
  private final JWTAuth jwtAuth;

  public Jwt(Vertx vertx) {
    this.jwtAuth = JWTAuth.create(vertx, new JWTAuthOptions()
        .addPubSecKey(new PubSecKeyOptions()
            .setAlgorithm("HS256")
            .setBuffer("my-secret")
        ));
  }
  
  public String generateToken(User user) {
    final var claims = new JsonObject();
    claims.put("email", user.getEmail());
    claims.put("id", user.getId());
    
    return generateToken(claims, 1000);
  }

  private String generateToken(JsonObject claims, int expirationInSeconds) {
      if (claims == null) {
          throw new IllegalArgumentException("Claims cannot be null");
      }
      
      return jwtAuth.generateToken(claims, new JWTOptions().setExpiresInSeconds(expirationInSeconds));
  }  
}
