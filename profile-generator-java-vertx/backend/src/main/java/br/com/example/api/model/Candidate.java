package br.com.example.api.model;

import io.vertx.core.json.JsonObject;

public class Candidate {
  
  private long id;
  private String name;
  private String address;
  private String phone;
  private String description;
  private long userId;
    
  public Candidate() {
  }

  public Candidate(String name, String address, String phone, String description, long userId) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.description = description;
    this.userId = userId;
  }

  public Candidate(long id, String name, String address, String phone, String description, long userId) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.description = description;
    this.userId = userId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public static Candidate getCandidateFromJsonObject(JsonObject candidate) {
    return new Candidate(candidate.getString("name"), candidate.getString("address"), candidate.getString("phone"), candidate.getString("description"), candidate.getLong("userId"));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + (int) (userId ^ (userId >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Candidate other = (Candidate) obj;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (phone == null) {
      if (other.phone != null)
        return false;
    } else if (!phone.equals(other.phone))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (userId != other.userId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Candidate [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", description="
        + description + ", userId=" + userId + "]";
  }

}
