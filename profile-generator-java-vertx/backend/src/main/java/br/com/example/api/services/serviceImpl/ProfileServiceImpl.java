package br.com.example.api.services.serviceImpl;

import br.com.example.api.model.Candidate;
import br.com.example.api.model.Experience;
import br.com.example.api.model.Graduation;
import br.com.example.api.model.Lenguage;
import br.com.example.api.model.Link;
import br.com.example.api.model.Profile;
import br.com.example.api.model.Skill;
import br.com.example.api.services.service.ProfileService;
import br.com.example.api.web.response.Response;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProfileServiceImpl implements ProfileService {

  private RoutingContext context;
  private CandidateServiceImpl candidateServiceImpl;
  private ExperienceServiceImpl experienceServiceImpl;
  private GraduationServiceImpl graduationServiceImpl;
  private LinkServiceImpl linkServiceImpl;
  private SkillServiceImpl skillServiceImpl;
  private LenguageServiceImpl lenguageServiceImpl;

  public ProfileServiceImpl(RoutingContext context, Vertx vertx) {
    this.context = context;
    this.candidateServiceImpl = new CandidateServiceImpl(context, vertx);
    this.experienceServiceImpl = new ExperienceServiceImpl(context, vertx);
    this.graduationServiceImpl = new GraduationServiceImpl(context, vertx);
    this.linkServiceImpl = new LinkServiceImpl(context, vertx);
    this.skillServiceImpl = new SkillServiceImpl(context, vertx);
    this.lenguageServiceImpl = new LenguageServiceImpl(context, vertx);
  }

  @Override
  public void findProfile(long profileId, long candidateId) {
    final var profile = new Profile();

    fetchCandidate(candidateId, profile)
        .compose(v -> fetchExperiences(profileId, profile))
        .compose(v -> fetchGraduations(profileId, profile))
        .compose(v -> fetchLinks(profileId, profile))
        .compose(v -> fetchSkills(profileId, profile))
        .compose(v -> fetchLanguages(profileId, profile))
        .onComplete(result -> {
            if (result.succeeded()) {
                JsonObject response = buildResponse(profile);
                new Response(context).send(200, response);
            } else {
                new Response(context).send(500, new JsonObject().put("error", "Failed to fetch profile data"));
            }
        });
}

private Future<Void> fetchCandidate(long candidateId, Profile profile) {
    Promise<Void> promise = Promise.promise();
    candidateServiceImpl.findById(candidateId).onComplete(candidate -> {
        if (candidate.succeeded()) {
            profile.setCandidate(candidate.result());
            promise.complete();
        } else {
            promise.fail(candidate.cause());
        }
    });
    return promise.future();
}

private Future<Void> fetchExperiences(long profileId, Profile profile) {
    Promise<Void> promise = Promise.promise();
    experienceServiceImpl.findAllByProfileId(profileId).onComplete(experiences -> {
        if (experiences.succeeded()) {
            profile.setExperiences(experiences.result());
            promise.complete();
        } else {
            promise.fail(experiences.cause());
        }
    });
    return promise.future();
}

private Future<Void> fetchGraduations(long profileId, Profile profile) {
    Promise<Void> promise = Promise.promise();
    graduationServiceImpl.findByProfileId(profileId).onComplete(graduations -> {
        if (graduations.succeeded()) {
            profile.setGraduations(graduations.result());
            promise.complete();
        } else {
            promise.fail(graduations.cause());
        }
    });
    return promise.future();
}

private Future<Void> fetchLinks(long profileId, Profile profile) {
    Promise<Void> promise = Promise.promise();
    linkServiceImpl.findAllByProfileId(profileId).onComplete(links -> {
        if (links.succeeded()) {
            profile.setLinks(links.result());
            promise.complete();
        } else {
            promise.fail(links.cause());
        }
    });
    return promise.future();
}

private Future<Void> fetchSkills(long profileId, Profile profile) {
    Promise<Void> promise = Promise.promise();
    skillServiceImpl.findAllByProfileId(profileId).onComplete(skills -> {
        if (skills.succeeded()) {
            profile.setSkills(skills.result());
            promise.complete();
        } else {
            promise.fail(skills.cause());
        }
    });
    return promise.future();
}

private Future<Void> fetchLanguages(long profileId, Profile profile) {
    Promise<Void> promise = Promise.promise();
    lenguageServiceImpl.findAllByProfileId(profileId).onComplete(languages -> {
        if (languages.succeeded()) {
            profile.setLenguages(languages.result());
            promise.complete();
        } else {
            promise.fail(languages.cause());
        }
    });
    return promise.future();
}

private JsonObject buildResponse(Profile profile) {
    JsonObject response = new JsonObject();
    response.put("candidate", Candidate.parseCandidateToJsonObject(profile.getCandidate()));
    response.put("experiences", Experience.parseExperiencesToJsonArray(profile.getExperiences()));
    response.put("graduations", Graduation.parseGraduationsToJsonArray(profile.getGraduations()));
    response.put("languages", Lenguage.parseLenguagesToJsonArray(profile.getLenguages()));
    response.put("links", Link.parseLinksToJsonArray(profile.getLinks()));
    response.put("skills", Skill.parseSkillsToJsonArray(profile.getSkills()));
    return response;
}
}
