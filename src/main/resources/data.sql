--username=example@email.com
--password=pass

INSERT INTO users (username, [password], enabled) 
VALUES ('example@email.com', '$2b$12$/f2bwFkp1Eqz8WFB.30M9O/r8.N5pLYKTQBtXtj8d1oe0XiFcZL12', 1);

INSERT INTO authorities (username, authority) 
VALUES ('example@email.com', 'ROLE_USER');
