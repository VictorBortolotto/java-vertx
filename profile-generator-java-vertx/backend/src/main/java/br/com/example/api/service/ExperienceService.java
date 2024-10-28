package br.com.example.api.service;

import java.sql.Date;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Experience;
import br.com.example.api.repository.ExperienceRepository;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

public class ExperienceService implements ExperienceRepository {

  private RoutingContext context;
  private Vertx vertx;

  public ExperienceService(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public Future<Experience> findByProfileId(long id) {
    final Promise<Experience> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, name, address, phone, description from user where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      if (handle.succeeded() && handle.result().size() > 0) {
        for (Row row : handle.result()) {
          final var candidate = new Experience();
          candidate.setId(row.getLong("id"));
          candidate.setCompanyName(row.getString("company_name"));
          candidate.setDescription(row.getString("description"));
          candidate.setStartDate(Date.valueOf(row.getString("start_date")));
          candidate.setEndDate(Date.valueOf(row.getString("end_date")));
          candidate.setActualWork(row.getBoolean("actual_work"));
          
          promise.complete(candidate);
        } 
      } else {
        promise.complete(new Experience());
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find user");
    });

    return promise.future();
  }

  @Override
  public void save(Experience experience, long profileId) {
    final Tuple params = Tuple.of(
      experience.getCompanyName(),
      experience.getDescription(),
      experience.getStartDate(),
      experience.getEndDate(),
      experience.isActualWork()
    );
    
    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("insert into experience(company_name,description,start_date,end_date,actual_work) values (?,?,?,?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        experience.setId(handle.result().property(MySQLClient.LAST_INSERTED_ID));
        save(experience.getId(), profileId, connection).onComplete(handleSave -> {
          if (handleSave.succeeded()) {
            new Response(context).send(200, new JsonObject().put("experience", experience));
          }
        });
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }

  private Future<Void> save(long experienceId, long profileId, SqlClient connection) {
    final Promise<Void> promise = Promise.promise();
    final var params = Tuple.of(profileId, experienceId);

    connection.preparedQuery("insert into profile_experience(profile_id, experience_id) values (?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        promise.complete();
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });

    return promise.future();
  }

  @Override
  public void delete(long idProfile, long idExperience) {
    final var connection = DatabaseService.startConnection(vertx);
    connection.preparedQuery("delete from profile_experience where profile_id = ? and experience_id = ?").execute(Tuple.of(idProfile, idExperience)).onComplete(handle -> {
      if (handle.succeeded()) {
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }
  
  public Future<Void> delete(long idExperience) {
    final Promise<Void> promise = Promise.promise();
    final var connection = DatabaseService.startConnection(vertx);
    connection.preparedQuery("delete from experience where and id = ?").execute(Tuple.of(idExperience)).onComplete(handle -> {
      if (handle.succeeded()) {
        promise.complete();
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });

    return promise.future();
  }

}
