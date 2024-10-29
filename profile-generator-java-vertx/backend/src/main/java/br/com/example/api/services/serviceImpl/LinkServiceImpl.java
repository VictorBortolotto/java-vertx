package br.com.example.api.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import br.com.example.api.data.DatabaseService;
import br.com.example.api.model.Link;
import br.com.example.api.services.service.LinkService;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class LinkServiceImpl implements LinkService {

  private RoutingContext context;
  private Vertx vertx;

  public LinkServiceImpl(RoutingContext context, Vertx vertx) {
    this.vertx = vertx;
    this.context = context;
  }

  @Override
  public Future<List<Link>> findAllByProfileId(long profileId) {
    final Promise<List<Link>> promise = Promise.promise();

    DatabaseService.startConnection(vertx).preparedQuery("select id, name, address from link where profile_id = ?").execute(Tuple.of(profileId)).onComplete(handle -> {
      final var result = handle.result();
      if (handle.succeeded() && result.size() > 0) {
        final var links = new ArrayList<Link>();
        for (Row row : result) {
          final var link = new Link();
          link.setId(row.getLong("id"));
          link.setName(row.getString("name"));
          link.setAddress(row.getString("address"));
          links.add(link);
        } 
        promise.complete(links);
      } else {
        promise.fail("Fail to find link");
      }
    }).onFailure(handleFail -> {
      promise.fail(handleFail.getMessage());
      handleFail.printStackTrace();
      context.response().setStatusCode(500).send("Error to find link");
    });

    return promise.future();
  }

  @Override
  public void save(Link link) {
    final var params = Tuple.of(
      link.getName(),
      link.getAddress(),
      link.getProfileId());

    DatabaseService.startConnection(vertx).preparedQuery("insert into link(name,address,profile_id) values (?,?,?)").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        link.setId(DatabaseService.getLastInsertedId(handle));
        final var response = new JsonObject();
        response.put("message", "Link created with success!");
        response.put("body", Link.parseLinkToJsonObject(link));
        new Response(context).send(200, response);
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to save link - " + handleFail.getMessage()));
    });
  }

  @Override
  public void delete(long id) {
    DatabaseService.startConnection(vertx).preparedQuery("delete from link where id = ?").execute(Tuple.of(id)).onComplete(handle -> {
      if (handle.succeeded()) {
        new Response(context).send(200, new JsonObject().put("message", "Link deleted with success!"));
      } 
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to delete link - " + handleFail.getMessage()));
    });
  }

  @Override
  public void update(Link link, long id) {
    final var params = Tuple.of(
      link.getName(),
      link.getAddress(),
      id);

    final var connection = DatabaseService.startConnection(vertx);

    connection.preparedQuery("update link set name=?, address=? where id=?").execute(params).onComplete(handle -> {
      if (handle.succeeded()) {
        final var response = new JsonObject();
        response.put("message", "Link updated with success!");
      }
    }).onFailure(handleFail -> {
      handleFail.printStackTrace();
      new Response(context).send(500, new JsonObject().put("message", "Error to create candidate - " + handleFail.getMessage()));
    });
  }
}
