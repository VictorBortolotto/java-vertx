package br.com.example.api.model;

import java.time.LocalDateTime;
import java.util.List;

import br.com.example.api.utils.DateUtils;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Experience {
  
  private long id;
  private String companyName;
  private String description;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private boolean actualWork;
  private long profileId;
  
  public Experience() {
  }

  public Experience(long id, String companyName, String description, LocalDateTime startDate, LocalDateTime endDate, boolean actualWork, long profileId) {
    this.id = id;
    this.companyName = companyName;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.actualWork = actualWork;
    this.profileId = profileId;
  }

  public Experience(String companyName, String description, LocalDateTime startDate, LocalDateTime endDate, boolean actualWork, long profileId) {
    this.companyName = companyName;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.actualWork = actualWork;
    this.profileId = profileId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public boolean isActualWork() {
    return actualWork;
  }

  public void setActualWork(boolean actualWork) {
    this.actualWork = actualWork;
  }

  public long getProfileId() {
    return profileId;
  }

  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  public static Experience getExperienceFromJsonObject(JsonObject jsonObject) {
    return new Experience(jsonObject.getString("companyName"), jsonObject.getString("description"), DateUtils.parseStringToLocalDateTime(jsonObject.getString("startDate")), DateUtils.parseStringToLocalDateTime(jsonObject.getString("endDate")), jsonObject.getBoolean("actualWork"), jsonObject.getLong("profileId"));
  }

  public static JsonObject parseExperienceToJsonObject(Experience experience) {
    final var jsonObject = new JsonObject();
    jsonObject.put("companyName", experience.getCompanyName());
    jsonObject.put("description", experience.getDescription());
    jsonObject.put("startDate", experience.getStartDate().toString());
    jsonObject.put("endDate", experience.getEndDate().toString());
    jsonObject.put("actualWork", experience.isActualWork());
    jsonObject.put("profileId", experience.getProfileId());
    jsonObject.put("experienceId", experience.getId());

    return jsonObject;
  }

  public static JsonArray parseExperiencesToJsonArray(List<Experience> experiences) {
    final var jsonArray = new JsonArray();
    for (Experience experience : experiences) {
      final var jsonObject = parseExperienceToJsonObject(experience);
      jsonArray.add(jsonObject);
    }

    return jsonArray;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + (actualWork ? 1231 : 1237);
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
    Experience other = (Experience) obj;
    if (id != other.id)
      return false;
    if (companyName == null) {
      if (other.companyName != null)
        return false;
    } else if (!companyName.equals(other.companyName))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
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
    if (actualWork != other.actualWork)
      return false;
    if (profileId != other.profileId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Experience [id=" + id + ", companyName=" + companyName + ", description=" + description + ", startDate="
        + startDate + ", endDate=" + endDate + ", actualWork=" + actualWork + ", profileId=" + profileId + "]";
  }
}
