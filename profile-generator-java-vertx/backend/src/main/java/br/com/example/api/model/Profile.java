package br.com.example.api.model;

import java.util.List;

public class Profile {
  
  private long id;
  private long candidateId;
  private Candidate candidate;
  private List<Experience> experiences;
  private List<Link> links;
  private List<Graduation> graduations;
  private List<Skill> skills;
  private List<Lenguage> lenguages;

  public Profile() {
  }

  public Profile(long id, long candidateId) {
    this.id = id;
    this.candidateId = candidateId;
  }

  public Profile(Candidate candidate, List<Experience> experiences, List<Link> links, List<Graduation> graduations,
      List<Skill> skills, List<Lenguage> lenguages) {
    this.candidate = candidate;
    this.experiences = experiences;
    this.links = links;
    this.graduations = graduations;
    this.skills = skills;
    this.lenguages = lenguages;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getCandidateId() {
    return candidateId;
  }

  public void setCandidateId(long candidateId) {
    this.candidateId = candidateId;
  }

  public Candidate getCandidate() {
    return candidate;
  }

  public void setCandidate(Candidate candidate) {
    this.candidate = candidate;
  }

  public List<Experience> getExperiences() {
    return experiences;
  }

  public void setExperiences(List<Experience> experiences) {
    this.experiences = experiences;
  }

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public List<Graduation> getGraduations() {
    return graduations;
  }

  public void setGraduations(List<Graduation> graduations) {
    this.graduations = graduations;
  }

  public List<Skill> getSkills() {
    return skills;
  }

  public void setSkills(List<Skill> skills) {
    this.skills = skills;
  }

  public List<Lenguage> getLenguages() {
    return lenguages;
  }

  public void setLenguages(List<Lenguage> lenguages) {
    this.lenguages = lenguages;
  }

  @Override
  public String toString() {
    return "Profile [id=" + id + ", candidateId=" + candidateId + ", candidate=" + candidate + ", experiences="
        + experiences + ", links=" + links + ", graduations=" + graduations + ", skills=" + skills + ", lenguages="
        + lenguages + "]";
  }
}
