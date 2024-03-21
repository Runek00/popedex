insert into users (id, username, password, visible_name, email, register_time, enabled) values(0, 'test', 'test', 'test', 'test@te.st', now(), true) on conflict do nothing;

insert into users (id, username, password, visible_name, email, register_time, enabled) values(1, 'test2', 'test2', 'test2', 'test2@te.st', now(), true) on conflict do nothing;

alter sequence if exists users_id_seq restart with 5;
