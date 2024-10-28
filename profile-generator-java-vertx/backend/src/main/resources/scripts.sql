create database my_profile;
use my_profile;
 
create table user(
	id int primary key auto_increment,
  email varchar(500) unique not null,
  password varchar(500) not null
);

create table candidate(
  id int primary key auto_increment,
  name varchar(500) not null,
  address varchar(500) not null,
  phone varchar(400) not null,
  description varchar(2000) null,
  user_id int not null
);

create table profile(
  id int primary key auto_increment,
  candidate_id int not null
);

create table profile_experience(
  profile_id int,
  experience_id int
);

create table experience(
  id int primary key auto_increment,
  company_name varchar(500) not null,
  description varchar(2000) not null,
  start_date date not null,
  end_date date null,
  actual_work boolean default 0
);

create table lenguage(
  id int primary key auto_increment,
  name varchar(500) not null,
  proficiency varchar(500) not null
);

create table profile_lenguage(
  profile_id int,
  lenguage_id int
);

create table graduation(
  id int primary key auto_increment,
  name varchar(500) not null,
  start_date date not null,
  end_date date null,
  finished boolean default 0
);

create table profile_graduation(
  profile_id int,
  graduation_id int
);

create table link(
  id int primary key auto_increment,
  name varchar(500) not null,
  address varchar(1000) not null
);

create table profile_link(
  profile_id int,
  link_id int
);

create table skill(
  id int primary key auto_increment,
  name varchar(500) not null
);

create table profile_skill(
  profile_id int,
  skill_id int
);

alter table candidate add constraint fk_user foreign key candidate(user_id) references user(id);

alter table profile add constraint fk_candidate_profile foreign key profile(candidate_id) references candidate(id);

alter table profile_experience add constraint fk_profile_profile_experience foreign key profile_experience(profile_id) references profile(id);
alter table profile_experience add constraint fk_experience_profile_experience foreign key profile_experience(experience_id) references experience(id);

alter table profile_graduation add constraint fk_profile_profile_graduation foreign key profile_graduation(profile_id) references profile(id);
alter table profile_graduation add constraint fk_graduation_profile_graduation foreign key profile_graduation(graduation_id) references graduation(id);

alter table profile_link add constraint fk_profile_profile_link foreign key profile_link(profile_id) references profile(id);
alter table profile_link add constraint fk_link_profile_link foreign key profile_link(link_id) references link(id);

alter table profile_skill add constraint fk_profile_profile_skill foreign key profile_skill(profile_id) references profile(id);
alter table profile_skill add constraint fk_skill_profile_skill foreign key profile_skill(skill_id) references skill(id);