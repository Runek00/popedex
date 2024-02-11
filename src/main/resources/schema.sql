create schema if not exists statue_info;

create table if not exists statue_info.statue(
    id bigserial primary key,
    location_name text not null,
    unveiling_date date,
    "exists" bool not null default true,
    added_by int8,
    active bool not null default true
);

create table if not exists users(
    id bigserial,
 	username varchar(50) not null primary key,
 	password varchar(200) not null,
 	email text,
 	register_time timestamp not null,
 	enabled boolean not null
 );
create unique index if not exists id_idx on users (id);

 create table if not exists authorities (
 	username varchar(50) not null,
 	authority varchar(50) not null,
 	constraint fk_authorities_users foreign key(username) references users(username)
 );
 create unique index if not exists ix_auth_username on authorities (username,authority);