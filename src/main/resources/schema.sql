DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS role_permissions CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(200),
    middle_name VARCHAR(200),
    last_name VARCHAR(200),
    second_last_name VARCHAR(200),
    phone_number VARCHAR(20),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500),
    enabled BOOLEAN NOT NULL,
    token VARCHAR(100),
    token_expiration_date TIMESTAMP
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500)
);

CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    permission VARCHAR(100) NOT NULL
);

CREATE TABLE role_permissions (
    id SERIAL PRIMARY KEY,
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    CONSTRAINT fk_role_permissions_roles FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_role_permissions_permissions FOREIGN KEY (permission_id) REFERENCES permissions(id),
    CONSTRAINT uq_role_permissions_role_permission UNIQUE (role_id, permission_id)
);

CREATE TABLE user_roles (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT fk_role_members_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_role_members_roles FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT uq_role_members_user_role UNIQUE (user_id, role_id)
);