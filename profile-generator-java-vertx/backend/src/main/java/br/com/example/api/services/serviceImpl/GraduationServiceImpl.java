package br.com.example.api.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Graduation;
import br.com.example.api.services.service.GraduationService;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class GraduationServiceImpl implements GraduationService {

  private RoutingContext context;
  private Vertx vertx;

  public GraduationServiceImpl(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public void save(Graduation graduation) {
    final Tuple params = Tuple.of(
      graduation.getName(),
      graduation.getStartDate(),
      graduation.getEndDate(),
      graduation.isFinished(),
      graduation.getProfileId()
    );
    
    DatabaseService.startConnection(vertx).preparedQuery("insert into graduation(name,start_date,end_date,finished,profile_id) values (?,?,?,?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        graduation.setId(DatabaseService.getLastInsertedId(handle));
        final var response = new JsonObject();
        response.put("message", "Graduation created with success!");
        response.put("body", Graduation.parseGraduationToJsonObject(graduation));
        new Response(context).send(200, response);
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to insert graduation - " + handleFail.getMessage()));
    });
  }

  @Override
  public Future<List<Graduation>> findByProfileId(long profileId) {
    final Promise<List<Graduation>> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, name, start_date, end_date, finished from graduation where profile_id = ?").execute(Tuple.of(profileId)).onComplete(handle -> {
      final var result = handle.result();
      if (handle.succeeded() && result.size() > 0) {
        final var graduations = new ArrayList<Graduation>();
        for (Row row : result) {
          final var graduation = new Graduation();
          graduation.setId(row.getLong("id"));
          graduation.setName(row.getString("name"));
          graduation.setStartDate(row.getLocalDateTime("start_date"));
          graduation.setEndDate(row.getLocalDateTime("end_date"));
          graduation.setFinished(row.getBoolean("finished"));
          graduations.add(graduation);
        } 
        promise.complete(graduations);
      } else if (handle.succeeded() && result.size() == 0) {
        promise.complete(List.of(new Graduation()));
      } else {
        promise.fail("Fail to find graduation");
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find graduation");
    });

    return promise.future();
  }

  @Override
  public void delete(long id) {
    DatabaseService.startConnection(vertx).preparedQuery("delete from graduation where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      if (handle.succeeded()) {
        new Response(context).send(200, new JsonObject().put("message", "Graduation deleted with success!"));
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to delete graduation - " + handleFail.getMessage()));
    });
  }

  @Override
  public void update(Graduation graduation, long id) {
    final var params = Tuple.of(
      graduation.getName(),
      graduation.getStartDate(),
      graduation.getEndDate(),
      graduation.isFinished(),
      id);

    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("update graduation set name=?, start_date=?, end_date=?, finished=? where id=?").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        final var response = new JsonObject();
        response.put("message", "Graduation updated with success!");
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }
}
