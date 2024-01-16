create schema if not exists statue_info;

create table if not exists statue_info.statue(
    id bigserial primary key,
    location_name text not null,
    unveiling_date date,
    "exists" bool not null default true,
    added_by int8,
    active bool not null default true
);