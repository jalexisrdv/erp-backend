DROP TABLE IF EXISTS inventory_out_movements CASCADE;
DROP TABLE IF EXISTS inventory_entry_movements CASCADE;
DROP TABLE IF EXISTS inventory CASCADE;
DROP TABLE IF EXISTS item_category CASCADE;
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
    CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_roles FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT uq_user_roles_user_role UNIQUE (user_id, role_id)
);

CREATE TABLE item_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500)
);

CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    item_category_id VARCHAR(100) NOT NULL,
    item_code VARCHAR(50) UNIQUE NOT NULL,
    item_name TEXT,
    minimum_stock NUMERIC(10,2) DEFAULT 0,
    created_by INT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_by INT,
    updated_at TIMESTAMP,
    CONSTRAINT fk_inventory_users_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_inventory_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);

CREATE TABLE inventory_entry_movements (
    id SERIAL PRIMARY KEY,
    inventory_id INT NOT NULL,
    quantity NUMERIC(10,2) NOT NULL CHECK (quantity > 0),
    created_by INT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_by INT,
    updated_at TIMESTAMP,
    reviewed_by INT,
    reviewed_at TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (status IN ('PENDIENTE', 'APROBADO', 'RECHAZADO')),
    rejected_reason TEXT,
    CONSTRAINT fk_inventory_entry_movements_inventory FOREIGN KEY (inventory_id) REFERENCES inventory(id),
    CONSTRAINT fk_inventory_entry_movements_users_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_inventory_entry_movements_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_inventory_entry_movements_users_reviewed_by FOREIGN KEY (reviewed_by) REFERENCES users(id)
);

CREATE TABLE inventory_output_movements (
    id SERIAL PRIMARY KEY,
    inventory_id INT NOT NULL,
    quantity NUMERIC(10,2) NOT NULL CHECK (quantity > 0),
    output_reason TEXT,
    created_by INT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_by INT,
    updated_at TIMESTAMP,
    reviewed_by INT,
    reviewed_at TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (status IN ('PENDIENTE', 'APROBADO', 'RECHAZADO')),
    rejected_reason TEXT,
    CONSTRAINT fk_inventory_output_movements_inventory FOREIGN KEY (inventory_id) REFERENCES inventory(id),
    CONSTRAINT fk_inventory_output_movements_users_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_inventory_output_movements_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_inventory_output_movements_users_reviewed_by FOREIGN KEY (reviewed_by) REFERENCES users(id)
);