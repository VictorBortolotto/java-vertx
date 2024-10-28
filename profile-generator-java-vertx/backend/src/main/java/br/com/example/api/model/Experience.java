package br.com.example.api.model;

import java.util.Date;

public class Experience {
  
  private long id;
  private String companyName;
  private String description;
  private Date startDate;
  private Date endDate;
  private boolean actualWork;
  
  public Experience() {
  }

  public Experience(long id, String companyName, String description, Date startDate, Date endDate, boolean actualWork) {
    this.id = id;
    this.companyName = companyName;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.actualWork = actualWork;
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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public boolean isActualWork() {
    return actualWork;
  }

  public void setActualWork(boolean actualWork) {
    this.actualWork = actualWork;
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
    return true;
  }

  @Override
  public String toString() {
    return "Experience [id=" + id + ", companyName=" + companyName + ", description=" + description + ", startDate="
        + startDate + ", endDate=" + endDate + ", actualWork=" + actualWork + "]";
  }

}
