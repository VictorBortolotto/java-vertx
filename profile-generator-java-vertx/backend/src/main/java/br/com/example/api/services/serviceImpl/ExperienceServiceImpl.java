package br.com.example.api.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Experience;
import br.com.example.api.services.service.ExperienceService;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class ExperienceServiceImpl implements ExperienceService {

  private RoutingContext context;
  private Vertx vertx;

  public ExperienceServiceImpl(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public Future<List<Experience>> findAllByProfileId(long profileId) {
    final Promise<List<Experience>> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, company_name, description, start_date, end_date, actual_work from experience where profile_id = ?").execute(Tuple.of(profileId)).onComplete(handle -> {
      final var result = handle.result();
      if (handle.succeeded() && result.size() > 0) {
        final var experiences = new ArrayList<Experience>();
        for (Row row : result) {
          final var experience = new Experience();
          experience.setId(row.getLong("id"));
          experience.setCompanyName(row.getString("company_name"));
          experience.setDescription(row.getString("description"));
          experience.setStartDate(row.getLocalDateTime("start_date"));
          experience.setEndDate(row.getLocalDateTime("end_date"));
          experience.setActualWork(row.getBoolean("actual_work"));
          experiences.add(experience);
        } 
        promise.complete(experiences);
      } else if (handle.succeeded() && result.size() == 0) {
        promise.complete(List.of(new Experience()));
      } else {
        promise.fail("Fail to find experience");
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find experience");
    });

    return promise.future();
  }

  @Override
  public void save(Experience experience) {
    final Tuple params = Tuple.of(
      experience.getCompanyName(),
      experience.getDescription(),
      experience.getStartDate(),
      experience.getEndDate(),
      experience.isActualWork(),
      experience.getProfileId()
    );
    
    DatabaseService.startConnection(vertx).preparedQuery("insert into experience(company_name,description,start_date,end_date,actual_work,profile_id) values (?,?,?,?,?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        experience.setId(DatabaseService.getLastInsertedId(handle));
        final var response = new JsonObject();
        response.put("message", "Experience created with success!");
        response.put("body", Experience.parseExperienceToJsonObject(experience));

        new Response(context).send(200, response);
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create experience - " + handleFail.getMessage()));
    });
  }

  @Override
  public void delete(long id) {
    DatabaseService.startConnection(vertx).preparedQuery("delete from experience where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      if (handle.succeeded()) {
        new Response(context).send(200, new JsonObject().put("message", "Experience deleted with success!"));
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to delete experience - " + handleFail.getMessage()));
    });
  }

  @Override
  public void update(Experience experience, long id) {
    final var params = Tuple.of(
      experience.getCompanyName(),
      experience.getDescription(),
      experience.getStartDate(),
      experience.getEndDate(),
      experience.isActualWork(),
      id);

    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("update experience set company_name=?, description=?, start_date=?, end_date=?, actual_work=? where id=?").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        final var response = new JsonObject();
        response.put("message", "Experience updated with success!");
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }
}
