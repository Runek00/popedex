insert into users (id, username, password, visible_name, email, register_time, enabled) values(0, 'test', 'test', 'test', 'test@te.st', now(), true);

insert into statue_info.statue(id, location_name, added_by) values(1, 'l1', 0), (2, 'l2', null), (3, 'l3', null);

insert into statue_info.user_statue(user_id, statue_id) values(0,1), (0,2), (0,3);

alter sequence if exists statue_info.statue_id_seq restart with 5;