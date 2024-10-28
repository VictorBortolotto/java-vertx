package br.com.example.api.service;

import br.com.example.api.authentication.auth.Authentication;
import br.com.example.api.authentication.jwt.Jwt;
import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.User;
import br.com.example.api.repository.UserRepository;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class UserService implements UserRepository {

  private RoutingContext context;
  private Vertx vertx;

  public UserService(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public void save(User user) {
    findByEmail(user.getEmail()).onSuccess(result -> {
      if (result.getId() > 0) {
        new Response(context).send(409, new JsonObject().put("message", "Email already exists."));
      } else {
        final var params = Tuple.of(user.getEmail(), Authentication.generateHash(user.getPassword()));
        DatabaseService.startConnection(vertx).preparedQuery("insert into user(email,password) values (?, ?)").execute(params).onComplete(handle -> {
          if (handle.succeeded()) {
            user.setId(handle.result().property(MySQLClient.LAST_INSERTED_ID));
            final var token = new Jwt(vertx).generateToken(user);
            final var jsonObject = new JsonObject();
            jsonObject.put("token", token);

            new Response(context).send(200, jsonObject);
          }
        }).onFailure(handleFail -> {
          handleFail.printStackTrace();
          new Response(context).send(500, new JsonObject().put("message", "Error to create user - " + handleFail.getMessage()));
        });
      }
    });
  }

  public void login(User user) {
    findByEmail(user.getEmail()).onSuccess(result -> {
      if (result.getId() == 0) {
        new Response(context).send(404, new JsonObject().put("message", "User not found."));
      } else {
        if (!Authentication.verifyPassword(user.getPassword(), result.getPassword())) {
          new Response(context).send(401, new JsonObject().put("message", "Wrong password."));
        } else {
          final var token = new Jwt(vertx).generateToken(result);
          final var jsonObject = new JsonObject();
          jsonObject.put("token", token);
  
          new Response(context).send(200, jsonObject);
        }
      }
    });
  }

  @Override
  public Future<User> findByEmail(String email) {
    final Promise<User> promise = Promise.promise();
    
    DatabaseService.startConnection(vertx).preparedQuery("select id, email, password from user where email = ?").execute(Tuple.of(email)).onComplete(handle -> {
      if (handle.succeeded() && handle.result().size() > 0) {
        for (Row row : handle.result()) {
          final var user = new User();
          user.setId(row.getLong("id"));
          user.setEmail(row.getString("email"));
          user.setPassword(row.getString("password"));
          promise.complete(user);
        } 
      } else {
        promise.complete(new User());
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find user");
    });

    return promise.future();
  }
  
}
