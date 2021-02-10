INSERT IGNORE INTO car_type(id, car_type_name) VALUES(1,'SUV');
INSERT IGNORE INTO car_type(id, car_type_name) VALUES(2,'BERLINA');
INSERT IGNORE INTO car_type(id, car_type_name) VALUES(3,'VAN');
INSERT IGNORE INTO car_type(id, car_type_name) VALUES(4,'UTILITARIA');
INSERT IGNORE INTO car_type(id, car_type_name) VALUES(5,'CITYCAR');
INSERT IGNORE INTO car_type(id, car_type_name) VALUES(6,'FUORISTRADA');


INSERT IGNORE INTO role(name) VALUES('ROLE_CUSTOMER');
INSERT IGNORE INTO role(name) VALUES('ROLE_ADMIN');

INSERT IGNORE INTO user(id, name, surname, username, password, birthday) VALUES(1, 'ADMIN','ADMIN','ADMIN','$2a$10$Ppe98oK4RRWm4zZivfLzOO4aihe5sbKaRZ0Mm9tXBJT2IwHT3T53.','2021-01-01');
INSERT IGNORE INTO user_roles(user_id, roles_id) VALUES (1, 2)
