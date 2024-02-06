create schema if not exists statue_info;

create table if not exists statue_info.statue(
    id bigserial primary key,
    location_name text not null,
    unveiling_date date,
    "exists" bool not null default true,
    added_by int8,
    active bool not null default true
);

create table if not exists "user"(
    id bigserial primary key,
    login text not null,
    password text not null default 'default_password',
    email text,
    register_time timestamp not null,
    active bool not null default true
);