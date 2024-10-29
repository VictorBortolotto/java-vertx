package br.com.example.api.model;

import java.util.List;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Lenguage {
  
  private long id;
  private String name;
  private String proficiency;
  private long profileId;

  public Lenguage() {
  }

  public Lenguage(long id, String name, String proficiency, long profileId) {
    this.id = id;
    this.name = name;
    this.proficiency = proficiency;
    this.profileId = profileId;
  }

  public Lenguage(String name, String proficiency, long profileId) {
    this.name = name;
    this.proficiency = proficiency;
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

  public String getProficiency() {
    return proficiency;
  }

  public void setProficiency(String proficiency) {
    this.proficiency = proficiency;
  }

  public long getProfileId() {
    return profileId;
  }

  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  public static Lenguage getLenguageFromJsonObject(JsonObject jsonObject) {
    return new Lenguage(jsonObject.getString("name"), jsonObject.getString("proficiency"), jsonObject.getLong("profileId"));
  }

  public static JsonObject parseLenguageToJsonObject(Lenguage lenguage) {
    final var jsonObject = new JsonObject();
    jsonObject.put("name", lenguage.getName());
    jsonObject.put("proficiency", lenguage.getProficiency());
    jsonObject.put("profileId", lenguage.getProfileId());
    jsonObject.put("lenguageId", lenguage.getId());

    return jsonObject;
  }

  public static JsonArray parseLenguagesToJsonArray(List<Lenguage> lenguages) {
    final var jsonArray = new JsonArray();
    for (Lenguage lenguage : lenguages) {
      final var jsonObject = parseLenguageToJsonObject(lenguage);
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
    result = prime * result + ((proficiency == null) ? 0 : proficiency.hashCode());
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
    Lenguage other = (Lenguage) obj;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (proficiency == null) {
      if (other.proficiency != null)
        return false;
    } else if (!proficiency.equals(other.proficiency))
      return false;
    if (profileId != other.profileId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Lenguage [id=" + id + ", name=" + name + ", proficiency=" + proficiency + ", profileId=" + profileId + "]";
  }
}
