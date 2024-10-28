package br.com.example.api.model;

import io.vertx.core.json.JsonObject;

public class User {
  
  private long id;
  private String email;
  private String password;
  
  public User() {}

  public User(long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public static User getUserFromJsonObject(JsonObject jsonObject) {
    return new User(jsonObject.getString("email"), jsonObject.getString("password"));
  }  
}
