package br.com.example.api.model;

import java.util.List;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Skill {
  
  private long id;
  private String name;
  private long profileId;

  public Skill() {
  }
  
  public Skill(long id, String name, long profileId) {
    this.id = id;
    this.name = name;
    this.profileId = profileId;
  }

  public Skill(String name, long profileId) {
    this.name = name;
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

  public long getProfileId() {
    return profileId;
  }

  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  public static Skill getSkillFromJsonObject(JsonObject jsonObject) {
    return new Skill(jsonObject.getString("name"), jsonObject.getLong("profileId"));
  }

  public static JsonObject parseSkillToJsonObject(Skill skill) {
    final var jsonObject = new JsonObject();
    jsonObject.put("name", skill.getName());
    jsonObject.put("profileId", skill.getProfileId());
    jsonObject.put("linkId", skill.getId());

    return jsonObject;
  }

  public static JsonArray parseSkillsToJsonArray(List<Skill> skills) {
    final var jsonArray = new JsonArray();
    for (Skill skill : skills) {
      final var jsonObject = parseSkillToJsonObject(skill);
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
    Skill other = (Skill) obj;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (profileId != other.profileId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Skill [id=" + id + ", name=" + name + ", profileId=" + profileId + "]";
  }
}
