insert into users (id, username, password, email, register_time, enabled) values(0, 'test', 'test', 'test@te.st', now(), true);

insert into statue_info.statue(id, location_name) values(1, 'l1'), (2, 'l2'), (3, 'l3');

insert into statue_info.user_statue(user_id, statue_id) values(0,1), (0,2), (0,3);