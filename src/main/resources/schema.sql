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
 	visible_name varchar(50) not null,
 	email text,
 	register_time timestamp not null,
 	enabled bool not null,
 	from_oauth bool not null default false
 );
create unique index if not exists id_idx on users (id);

 create table if not exists authorities (
 	username varchar(50) not null,
 	authority varchar(50) not null,
 	constraint fk_authorities_users foreign key(username) references users(username)
 );
 create unique index if not exists ix_auth_username on authorities (username,authority);

create table if not exists statue_info.user_statue(
    id bigserial primary key,
    user_id int8 not null,
    statue_id int8 not null,
    found_date date,
 	constraint fk_user_id foreign key(user_id) references users(id),
 	constraint fk_statue_id foreign key(statue_id) references statue_info.statue(id)
);

create unique index if not exists ix_user_statue on statue_info.user_statue(user_id, statue_id);
