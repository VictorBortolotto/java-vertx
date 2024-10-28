package br.com.example.api.model;

public class Lenguage {
  
  private long id;
  private String name;
  private String proficiency;

  public Lenguage() {
  }

  public Lenguage(long id, String name, String proficiency) {
    this.id = id;
    this.name = name;
    this.proficiency = proficiency;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((proficiency == null) ? 0 : proficiency.hashCode());
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
    return true;
  }

  @Override
  public String toString() {
    return "Lenguage [id=" + id + ", name=" + name + ", proficiency=" + proficiency + "]";
  }
  
}
