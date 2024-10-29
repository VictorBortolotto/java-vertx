package br.com.example.api.model;

import java.util.List;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Link {

  private long id;
  private String name;
  private String address;
  private long profileId;

  public Link() {
  }
  
  public Link(long id, String name, String address, long profileId) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.profileId = profileId;
  }

  public Link(String name, String address, long profileId) {
    this.name = name;
    this.address = address;
    this.profileId = profileId;
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

  public long getProfileId() {
    return profileId;
  }

  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  public static Link getLinkFromJsonObject(JsonObject jsonObject) {
    return new Link(jsonObject.getString("name"), jsonObject.getString("address"), jsonObject.getLong("profileId"));
  }

  public static JsonObject parseLinkToJsonObject(Link link) {
    final var jsonObject = new JsonObject();
    jsonObject.put("name", link.getName());
    jsonObject.put("address", link.getAddress());
    jsonObject.put("profileId", link.getProfileId());
    jsonObject.put("linkId", link.getId());

    return jsonObject;
  }

  public static JsonArray parseLinksToJsonArray(List<Link> links) {
    final var jsonArray = new JsonArray();
    for (Link link : links) {
      final var jsonObject = parseLinkToJsonObject(link);
      jsonArray.add(jsonObject);
    }

    return jsonArray;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + (int) (profileId ^ (profileId >>> 32));
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
    Link other = (Link) obj;
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
    if (profileId != other.profileId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Link [id=" + id + ", name=" + name + ", address=" + address + ", profileId=" + profileId + "]";
  }
}
