package br.com.example.api.data;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;

public class DatabaseService {

  public static SqlClient startConnection(Vertx vertx) {
    return new DatabaseService().clientBuilder(vertx);
  }

  public static Long getLastInsertedId(AsyncResult<RowSet<Row>> handle) {
    return handle.result().property(MySQLClient.LAST_INSERTED_ID);
  }

  private SqlClient clientBuilder(Vertx vertx) {
    return MySQLBuilder.client()
      .with(new PoolOptions().setMaxSize(5))
      .connectingTo(connectOptions())
      .using(vertx)
      .build();
  }

  private MySQLConnectOptions connectOptions() {
    return new MySQLConnectOptions()
    .setPort(3306)
    .setHost("localhost")
    .setDatabase("my_profile")
    .setUser("root")
    .setPassword("123456");  }  

}
