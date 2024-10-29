package br.com.example.api.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Skill;
import br.com.example.api.services.service.SkillService;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class SkillServiceImpl implements SkillService {

  private RoutingContext context;
  private Vertx vertx;

  public SkillServiceImpl(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public Future<List<Skill>> findAllByProfileId(long profileId) {
    final Promise<List<Skill>> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, name, profile_id from skill where profile_id = ?").execute(Tuple.of(profileId)).onComplete(handle -> {
      final var result = handle.result();
      if (handle.succeeded() && result.size() > 0) {
        final var skills = new ArrayList<Skill>();
        for (Row row : result) {
          final var skill = new Skill();
          skill.setId(row.getLong("id"));
          skill.setName(row.getString("name"));
          skill.setProfileId(row.getLong("profile_id"));
          skills.add(skill);
        } 
        promise.complete(skills);
      } else {
        promise.fail("Fail to find skill");
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find skills");
    });

    return promise.future();
  }

  @Override
  public void save(Skill skill) {
    final var params = Tuple.of(
      skill.getName(),
      skill.getProfileId());

    DatabaseService.startConnection(vertx).preparedQuery("insert into skill(name,profile_id) values (?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        skill.setId(DatabaseService.getLastInsertedId(handle));
        final var response = new JsonObject();
        response.put("message", "Skill created with success!");
        response.put("body", Skill.parseSkillToJsonObject(skill));
        new Response(context).send(200, response);
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to save skill - " + handleFail.getMessage()));
    });
  }

  @Override
  public void delete(long id) {
    DatabaseService.startConnection(vertx).preparedQuery("delete from skill where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      if (handle.succeeded()) {
        new Response(context).send(200, new JsonObject().put("message", "Skill deleted with success!"));
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to delete lenguage - " + handleFail.getMessage()));
    });
  }  
}
