package br.com.example.api.service;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Candidate;
import br.com.example.api.repository.CandidateRepository;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class CandidateService implements CandidateRepository{

  private RoutingContext context;
  private Vertx vertx;

  public CandidateService(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public Future<Candidate> findById(long id) {
    final Promise<Candidate> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, name, address, phone, description from user where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      if (handle.succeeded() && handle.result().size() > 0) {
        for (Row row : handle.result()) {
          final var candidate = new Candidate();
          candidate.setId(row.getLong("id"));
          candidate.setName(row.getString("name"));
          candidate.setAddress(row.getString("adress"));
          candidate.setPhone(row.getString("phone"));
          candidate.setPhone(row.getString("description"));
          
          promise.complete(candidate);
        } 
      } else {
        promise.complete(new Candidate());
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

    DatabaseService.startConnection(vertx).preparedQuery("insert into candidate(name,address,phone,description,user_id) values (?,?,?,?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        candidate.setId(handle.result().property(MySQLClient.LAST_INSERTED_ID));
        final var jsonObject = new JsonObject();
        jsonObject.put("cancidateID", candidate.getId());

        new Response(context).send(200, jsonObject);
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }

  @Override
  public void update(Candidate candidate, long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

}
