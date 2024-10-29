package br.com.example.api.model;

import java.time.LocalDateTime;
import java.util.List;

import br.com.example.api.utils.DateUtils;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Graduation {
  
  private long id;
  private String name;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private boolean finished;
  private long profileId;
  
  public Graduation() {}

  public Graduation(long id, String name, LocalDateTime startDate, LocalDateTime endDate, boolean finished, long profileId) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.finished = finished;
    this.profileId = profileId;
  }

  public Graduation(String name, LocalDateTime startDate, LocalDateTime endDate, boolean finished, long profileId) {
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.finished = finished;
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

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  public long getProfileId() {
    return profileId;
  }

  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  public static Graduation getGraduationFromJsonObject(JsonObject jsonObject) {
    return new Graduation(jsonObject.getString("name"), DateUtils.parseStringToLocalDateTime(jsonObject.getString("startDate")), DateUtils.parseStringToLocalDateTime(jsonObject.getString("endDate")), jsonObject.getBoolean("finished"), jsonObject.getLong("profileId"));
  }

  public static JsonObject parseGraduationToJsonObject(Graduation graduation) {
    final var jsonObject = new JsonObject();
    jsonObject.put("name", graduation.getName());
    jsonObject.put("startDate", graduation.getStartDate().toString());
    jsonObject.put("endDate", graduation.getEndDate().toString());
    jsonObject.put("finished", graduation.isFinished());
    jsonObject.put("profileId", graduation.getProfileId());
    jsonObject.put("graduationId", graduation.getId());

    return jsonObject;
  }

  public static JsonArray parseGraduationsToJsonArray(List<Graduation> graduations) {
    final var jsonArray = new JsonArray();
    for (Graduation graduation : graduations) {
      final var jsonObject = parseGraduationToJsonObject(graduation);
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
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + (finished ? 1231 : 1237);
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
    Graduation other = (Graduation) obj;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (startDate == null) {
      if (other.startDate != null)
        return false;
    } else if (!startDate.equals(other.startDate))
      return false;
    if (endDate == null) {
      if (other.endDate != null)
        return false;
    } else if (!endDate.equals(other.endDate))
      return false;
    if (finished != other.finished)
      return false;
    if (profileId != other.profileId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Graduation [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
        + ", finished=" + finished + ", profileId=" + profileId + "]";
  }
}
