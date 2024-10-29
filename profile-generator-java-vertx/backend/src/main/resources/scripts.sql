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

create table experience(
  id int primary key auto_increment,
  company_name varchar(500) not null,
  description varchar(2000) not null,
  start_date datetime not null,
  end_date datetime null,
  actual_work boolean default 0,
  profile_id int
);

create table lenguage(
  id int primary key auto_increment,
  name varchar(500) not null,
  proficiency varchar(500) not null,
  profile_id int
);

create table graduation(
  id int primary key auto_increment,
  name varchar(500) not null,
  start_date datetime not null,
  end_date datetime null,
  finished boolean default 0,
  profile_id int
);

create table link(
  id int primary key auto_increment,
  name varchar(500) not null,
  address varchar(1000) not null,
  profile_id int
);

create table skill(
  id int primary key auto_increment,
  name varchar(500) not null,
  profile_id int
);

alter table candidate add constraint fk_user foreign key candidate(user_id) references user(id);

alter table profile add constraint fk_candidate_profile foreign key profile(candidate_id) references candidate(id);
alter table experience add constraint fk_profile_experience foreign key experience(profile_id) references profile(id);
alter table lenguage add constraint fk_profile_lenguage foreign key lenguage(profile_id) references profile(id);
alter table graduation add constraint fk_profile_graduation foreign key graduation(profile_id) references profile(id);
alter table link add constraint fk_profile_link foreign key link(profile_id) references profile(id);
alter table skill add constraint fk_profile_skill foreign key skill(profile_id) references profile(id);