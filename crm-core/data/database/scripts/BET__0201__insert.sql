
/******     USER        ******/
INSERT INTO user (id, username, password, name, status, role)
VALUES (1,'super-user','{bcrypt}$2a$10$RsICvB07LrQX6FjY/OjNlOqhPfbE3My8X0UVL9.8MWNIsC49tRhCy','Super User', 1,'ROLE_ADMIN'),
(2,'user','{bcrypt}$2a$10$RsICvB07LrQX6FjY/OjNlOqhPfbE3My8X0UVL9.8MWNIsC49tRhCy','User', 1,'ROLE_ADMIN');
