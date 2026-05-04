DROP TABLE IF EXISTS inventory_movements CASCADE;
DROP TABLE IF EXISTS inventory CASCADE;
DROP TABLE IF EXISTS item_categories CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS role_permissions CASCADE;
DROP TABLE IF EXISTS refresh_tokens CASCADE;
DROP TABLE IF EXISTS app_modules CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS report_responses CASCADE;
DROP TABLE IF EXISTS report_assignments CASCADE;
DROP TABLE IF EXISTS report_items CASCADE;
DROP TABLE IF EXISTS report_sections CASCADE;
DROP TABLE IF EXISTS report_templates CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(200) NOT NULL,
    middle_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    second_last_name VARCHAR(200) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(500) NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_expired BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT uq_users_username
        UNIQUE (username)
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500) NOT NULL,

    CONSTRAINT uq_roles_name
        UNIQUE (name)
);

CREATE TABLE refresh_tokens (
    id SERIAL PRIMARY KEY,
    token TEXT NOT NULL,
    user_id INT NOT NULL,
    expiry_date TIMESTAMPTZ NOT NULL,

    CONSTRAINT uq_refresh_token UNIQUE (token),

    CONSTRAINT fk_user_refresh_tokens
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE app_modules (
    id SERIAL PRIMARY KEY,
    code VARCHAR(200) NOT NULL,
    name VARCHAR(200) NOT NULL,
    parent_id INTEGER,

    CONSTRAINT uq_app_modules_code UNIQUE (code),
    CONSTRAINT uq_app_modules_name UNIQUE (name),
    CONSTRAINT fk_app_modules_parent_id
            FOREIGN KEY (parent_id)
            REFERENCES app_modules (id)
            ON DELETE SET NULL
);

CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    module_id INT REFERENCES app_modules(id) ON DELETE CASCADE,
    code VARCHAR(200) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,

    CONSTRAINT uq_permissions_code UNIQUE (code),
    CONSTRAINT uq_permissions_name UNIQUE (name)
);

CREATE TABLE role_permissions (
    id SERIAL PRIMARY KEY,
    role_id INT NOT NULL,
    permission_id INT NOT NULL,

    CONSTRAINT fk_role_permissions_roles
        FOREIGN KEY (role_id)
        REFERENCES roles(id),

    CONSTRAINT fk_role_permissions_permissions
        FOREIGN KEY (permission_id)
        REFERENCES permissions(id),

    CONSTRAINT uq_role_permissions_role_id_permission_id
        UNIQUE (role_id, permission_id)
);

CREATE TABLE user_roles (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    CONSTRAINT fk_user_roles_users
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_user_roles_roles
        FOREIGN KEY (role_id)
        REFERENCES roles(id),

    CONSTRAINT uq_user_roles_user_id_role_id
        UNIQUE (user_id, role_id)
);

CREATE TABLE item_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500) NOT NULL,

    CONSTRAINT uq_item_categories_name
        UNIQUE (name)
);

CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    item_category_id INT NOT NULL,
    item_code VARCHAR(50) NOT NULL,
    item_name TEXT,
    minimum_stock NUMERIC(10,2) DEFAULT 0,
    entry_count NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (entry_count >= 0),
    pending_entry_count NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (pending_entry_count >= 0),
    output_count NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (output_count >= 0),
    reserved_output_count NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (reserved_output_count >= 0),
    created_by INT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_by INT,
    updated_at TIMESTAMP,

    CONSTRAINT uq_inventory_item_code
        UNIQUE (item_code),

    CONSTRAINT fk_inventory_users_created_by
        FOREIGN KEY (created_by)
        REFERENCES users(id),

    CONSTRAINT fk_inventory_users_updated_by
        FOREIGN KEY (updated_by)
        REFERENCES users(id)
);

CREATE TABLE inventory_movements (
    id SERIAL PRIMARY KEY,
    inventory_id INT NOT NULL,
    type VARCHAR(20) DEFAULT '' NOT NULL CHECK (type IN ('ENTRADA', 'SALIDA')),
    quantity NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (quantity >= 0),
    invoice_url TEXT,
    output_reason TEXT,
    status VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (status IN ('PENDIENTE', 'APROBADO', 'RECHAZADO')),
    rejected_reason TEXT,
    created_by INT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_by INT,
    updated_at TIMESTAMP,
    reviewed_by INT,
    reviewed_at TIMESTAMP,

    CONSTRAINT fk_inventory_movements_inventory
        FOREIGN KEY (inventory_id)
        REFERENCES inventory(id),

    CONSTRAINT fk_inventory_movements_users_created_by
        FOREIGN KEY (created_by)
        REFERENCES users(id),

    CONSTRAINT fk_inventory_movements_users_updated_by
        FOREIGN KEY (updated_by)
        REFERENCES users(id),

    CONSTRAINT fk_inventory_movements_users_reviewed_by
        FOREIGN KEY (reviewed_by)
        REFERENCES users(id)
);

CREATE TABLE report_templates (
    id SERIAL PRIMARY KEY,
    name VARCHAR(1000) NOT NULL,

    CONSTRAINT uq_report_templates_name
        UNIQUE (name)
);

CREATE TABLE report_sections (
    id SERIAL PRIMARY KEY,
    uuid UUID DEFAULT gen_random_uuid() NOT NULL,
    template_id INT NOT NULL,
    name VARCHAR(1000) NOT NULL,
    position INT NOT NULL,

    CONSTRAINT uq_report_sections_uuid
        UNIQUE (uuid),

    CONSTRAINT uq_report_sections_template_id_name
        UNIQUE (template_id, name),

    CONSTRAINT fk_report_sections_report_templates
        FOREIGN KEY (template_id)
        REFERENCES report_templates (id)
        ON DELETE CASCADE
);

CREATE TABLE report_items (
    id SERIAL PRIMARY KEY,
    uuid UUID DEFAULT gen_random_uuid() NOT NULL,
    section_id INT NOT NULL,
    label TEXT NOT NULL,
    position INT NOT NULL,

    CONSTRAINT uq_report_items_uuid
            UNIQUE (uuid),

    CONSTRAINT uq_report_items_section_id_label
        UNIQUE (section_id, label),

    CONSTRAINT fk_report_items_report_sections
        FOREIGN KEY (section_id)
        REFERENCES report_sections (id)
        ON DELETE CASCADE
);

CREATE TABLE report_assignments (
    id SERIAL PRIMARY KEY,
    template_id INT NOT NULL,
    unit_number INT NOT NULL,
    operator_user_id INT NOT NULL,
    mechanic_user_id INT NOT NULL,
    mileage VARCHAR(1000) NOT NULL,
    next_service VARCHAR(1000) NOT NULL,
    time_in  TIME NOT NULL,
    time_out TIME NOT NULL,
    date DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(150) DEFAULT 'PENDIENTE',

    CONSTRAINT fk_report_assignments_report_templates
        FOREIGN KEY (template_id)
        REFERENCES report_templates (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_report_assignments_users_operator_user_id
        FOREIGN KEY (operator_user_id)
        REFERENCES users (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_report_assignments_users_mechanic_user_id
        FOREIGN KEY (mechanic_user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);

CREATE TABLE report_responses (
    id SERIAL PRIMARY KEY,
    assignment_id INT NOT NULL,
    item_id INT NOT NULL,
    status VARCHAR(3) NULL CHECK (status IN ('F', 'OK', 'R')),
    comment TEXT NULL,
    
    CONSTRAINT ck_report_responses_comment_when_not_ok
        CHECK (
            (status IS NULL)
            OR
            (status = 'OK')
            OR
            (status IN ('F', 'R') AND comment IS NOT NULL)
        ),
    
    CONSTRAINT fk_report_responses_report_assignments
        FOREIGN KEY (assignment_id)
        REFERENCES report_assignments (id)
        ON DELETE CASCADE,
    
    CONSTRAINT fk_report_responses_report_items
        FOREIGN KEY (item_id)
        REFERENCES report_items (id)
        ON DELETE CASCADE
);