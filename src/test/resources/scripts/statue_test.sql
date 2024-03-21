insert into users (id, username, password, visible_name, email, register_time, enabled) values(-1, 'statue_test', 'statue_test', 'statue_test', 'statue_test@te.st', now(), true) on conflict do nothing;

insert into statue_info.statue(id, location_name, added_by) values(1, 'l1', -1), (2, 'l2', null), (3, 'l3', null) on conflict do nothing;

insert into statue_info.user_statue(user_id, statue_id) values(-1,1), (-1,2), (-1,3) on conflict do nothing;

alter sequence if exists statue_info.statue_id_seq restart with 5;