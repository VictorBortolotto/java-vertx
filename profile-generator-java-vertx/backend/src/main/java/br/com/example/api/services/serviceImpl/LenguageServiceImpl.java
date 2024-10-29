package br.com.example.api.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Lenguage;
import br.com.example.api.services.service.LenguageService;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class LenguageServiceImpl implements LenguageService {

private RoutingContext context;
  private Vertx vertx;

  public LenguageServiceImpl(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public Future<List<Lenguage>> findAllByProfileId(long profileId) {
    final Promise<List<Lenguage>> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, name, proficiency from lenguage where profile_id = ?").execute(Tuple.of(profileId)).onComplete(handle -> {
      final var result = handle.result();
      if (handle.succeeded() && result.size() > 0) {
        final var lenguages = new ArrayList<Lenguage>();
        for (Row row : result) {
          final var lenguage = new Lenguage();
          lenguage.setId(row.getLong("id"));
          lenguage.setName(row.getString("name"));
          lenguage.setProficiency(row.getString("proficiency"));
          lenguages.add(lenguage);
        } 
        promise.complete(lenguages);
      } else {
        promise.fail("Fail to find lenguage");
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find user");
    });

    return promise.future();
  }

  @Override
  public void save(Lenguage lenguage) {
    final Tuple params = Tuple.of(
      lenguage.getName(),
      lenguage.getProficiency(),
      lenguage.getProfileId()
    );
    
    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("insert into lenguage(name,proficiency,profile_id) values (?,?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        final var response = new JsonObject();
        response.put("message", "Lenguage created with success!");
        response.put("body", Lenguage.parseLenguageToJsonObject(lenguage));
        new Response(context).send(200, response);
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }

  @Override
  public void delete(long id) {
    DatabaseService.startConnection(vertx).preparedQuery("delete from lenguage where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      if (handle.succeeded()) {
        new Response(context).send(200, new JsonObject().put("message", "Lenguage deleted with success!"));
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to delete lenguage - " + handleFail.getMessage()));
    });
  }

  @Override
  public void update(Lenguage lenguage, long id) {
    final var params = Tuple.of(
      lenguage.getName(),
      lenguage.getProficiency(),
      id);

    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("update lenguage set name=?, proficiency=? where id=?").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        final var response = new JsonObject();
        response.put("message", "Lenguage updated with success!");
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }
}
