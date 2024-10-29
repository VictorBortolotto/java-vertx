package br.com.example.api.services.serviceImpl;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Candidate;
import br.com.example.api.services.service.CandidateService;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

public class CandidateServiceImpl implements CandidateService{

  private RoutingContext context;
  private Vertx vertx;

  public CandidateServiceImpl(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public Future<Candidate> findById(long id) {
    final Promise<Candidate> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, name, address, phone, description from candidate where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      final var result = handle.result();
      if (handle.succeeded() && result.size() > 0) {
        final var candidate = new Candidate();
        for (Row row : result) {
          candidate.setId(row.getLong("id"));
          candidate.setName(row.getString("name"));
          candidate.setAddress(row.getString("address"));
          candidate.setPhone(row.getString("phone"));
          candidate.setDescription(row.getString("description"));
        } 
        promise.complete(candidate);
      } else {
        promise.fail("Fail to find candidate");
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find user");
    });

    return promise.future();
  }

  @Override
  public void save(Candidate candidate) {
    final var params = Tuple.of(
      candidate.getName(),
      candidate.getAddress(),
      candidate.getPhone(),
      candidate.getDescription(),
      candidate.getUserId());

    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("insert into candidate(name,address,phone,description,user_id) values (?,?,?,?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        final var candidateId = DatabaseService.getLastInsertedId(handle);
        save(connection, candidateId);
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }

  private void save(SqlClient connection, long candidateId) {
    connection.preparedQuery("insert into profile(candidate_id) values (?)").execute(Tuple.of(candidateId)).onComplete(handle -> {
      if (handle.succeeded()) {
        long profileId = DatabaseService.getLastInsertedId(handle);
        final var jsonObject = new JsonObject();
        jsonObject.put("cancidateID", candidateId);
        jsonObject.put("profileID", profileId);

        new Response(context).send(200, jsonObject);
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }

  @Override
  public void update(Candidate candidate, long id) {
    final var params = Tuple.of(
      candidate.getName(),
      candidate.getAddress(),
      candidate.getPhone(),
      candidate.getDescription(),
      id);

    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("update candidate set name=?, address=?, phone=?, description=? where id = ?").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        final var response = new JsonObject();
        response.put("message", "Candidate updated with success!");
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }
}
