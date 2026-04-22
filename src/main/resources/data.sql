INSERT INTO app_modules (code, name, parent_id) VALUES ('USER_MANAGEMENT', 'Administrar Usuarios', NULL);
INSERT INTO app_modules (code, name, parent_id) VALUES ('USERS', 'Usuarios', (SELECT id FROM app_modules WHERE code = 'USER_MANAGEMENT'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('USER_ROLES', 'Roles', (SELECT id FROM app_modules WHERE code = 'USER_MANAGEMENT'));

INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY', 'Inventario', NULL);
INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY_CATEGORIES', 'Categorías', (SELECT id FROM app_modules WHERE code = 'INVENTORY'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY_ITEMS', 'Artículos', (SELECT id FROM app_modules WHERE code = 'INVENTORY'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY_MOVEMENTS', 'Movimientos', (SELECT id FROM app_modules WHERE code = 'INVENTORY'));

INSERT INTO app_modules (code, name, parent_id) VALUES ('REPORTS', 'Reportes de Inspección', NULL);
INSERT INTO app_modules (code, name, parent_id) VALUES ('REPORT_TEMPLATES', 'Plantillas', (SELECT id FROM app_modules WHERE code = 'REPORTS'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('REPORT_ASSIGNMENTS', 'Asignaciones', (SELECT id FROM app_modules WHERE code = 'REPORTS'));

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USERS'), 'USERS_VIEW', 'Ver Usuarios', 'Permite visualizar la lista de usuarios');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USERS'), 'USERS_CREATE', 'Crear Usuarios', 'Permite registrar nuevos usuarios');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USERS'), 'USERS_EDIT', 'Editar Usuarios', 'Permite modificar datos de usuarios existentes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USERS'), 'USERS_DELETE', 'Eliminar Usuarios', 'Permite dar de baja o borrar usuarios');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USERS'), 'USERS_ASSIGN_ROLES', 'Asignar Roles', 'Permite agregar o gestionar roles de un usuario');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USERS'), 'USERS_RESET_PASSWORD', 'Resetear Contraseña', 'Permite restablecer las contraseñas de los usuarios');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'USERS_ROLES_VIEW', 'Ver Roles', 'Permite visualizar la lista de roles del sistema');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'USERS_ROLES_CREATE', 'Crear Roles', 'Permite registrar nuevos roles');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'USERS_ROLES_EDIT', 'Editar Roles', 'Permite modificar el nombre de los roles existentes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'USERS_ROLES_DELETE', 'Eliminar Roles', 'Permite borrar roles del sistema');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'USERS_ROLES_CONFIG', 'Configurar Permisos', 'Permite asignar o quitar permisos específicos a un rol');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'INVENTORY_CATEGORIES_VIEW', 'Ver Categorías', 'Permite visualizar la lista de categorías');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'INVENTORY_CATEGORIES_CREATE', 'Crear Categorías', 'Permite registrar nuevas categorías de inventario');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'INVENTORY_CATEGORIES_EDIT', 'Editar Categorías', 'Permite modificar el nombre o detalles de las categorías');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'INVENTORY_CATEGORIES_DELETE', 'Eliminar Categorías', 'Permite borrar categorías de inventario');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'INVENTORY_ITEMS_VIEW', 'Ver Artículos', 'Permite visualizar el catálogo de artículos');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'INVENTORY_ITEMS_CREATE', 'Crear Artículos', 'Permite registrar nuevos artículos en el catálogo');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'INVENTORY_ITEMS_EDIT', 'Editar Artículos', 'Permite modificar la información técnica o descriptiva de los artículos');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'INVENTORY_ITEMS_DELETE', 'Eliminar Artículos', 'Permite borrar artículos');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'INVENTORY_ITEMS_STOCK_IN', 'Agregar Entradas', 'Permite registrar ingresos de mercancía o incrementos de stock');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'INVENTORY_ITEMS_STOCK_OUT', 'Agregar Salidas', 'Permite registrar egresos de mercancía o disminuciones de stock');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_MOVEMENTS'), 'INVENTORY_MOVEMENTS_VIEW', 'Ver Movimientos', 'Permite visualizar el historial y estado de los movimientos');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_MOVEMENTS'), 'INVENTORY_MOVEMENTS_APPROVE', 'Aprobar Movimientos', 'Permite autorizar movimientos de inventario pendientes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_MOVEMENTS'), 'INVENTORY_MOVEMENTS_REJECT', 'Rechazar Movimientos', 'Permite denegar movimientos de inventario pendientes');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'REPORT_TEMPLATES_VIEW', 'Ver Plantillas', 'Permite visualizar el listado de plantillas de reporte');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'REPORT_TEMPLATES_CREATE', 'Crear Plantillas', 'Permite registrar nuevas plantillas de inspección');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'REPORT_TEMPLATES_EDIT', 'Editar Plantillas', 'Permite modificar la información general de las plantillas');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'REPORT_TEMPLATES_DELETE', 'Eliminar Plantillas', 'Permite borrar plantillas');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'REPORT_TEMPLATES_CONFIG', 'Configurar Plantillas', 'Permite configurar plantillas');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'REPORT_TEMPLATES_PREVIEW', 'Previsualizar Plantilla', 'Permite ver una vista previa del formato final de la plantilla');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'REPORT_ASSIGNMENTS_VIEW', 'Ver Asignaciones', 'Permite visualizar la lista de asignaciones de reportes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'REPORT_ASSIGNMENTS_CREATE', 'Crear Asignación', 'Permite asignar plantillas de inspección a usuarios');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'REPORT_ASSIGNMENTS_EDIT', 'Editar Asignación', 'Permite modificar los detalles de una asignación existente');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'REPORT_ASSIGNMENTS_DELETE', 'Eliminar Asignación', 'Permite borrar asignaciones');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'REPORT_ASSIGNMENTS_FILL_REPORT', 'Responder Reporte Asignado', 'Permite completar y enviar la información del reporte asignado');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'REPORT_ASSIGNMENTS_PREVIEW', 'Previsualizar Reporte Asignado', 'Permite visualizar el reporte finalizado antes o después de ser enviado');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');

INSERT INTO role_permissions (role_id, permission_id) SELECT 1, id FROM permissions;

--username=jalexisrdv
--password=jalexisrdv
INSERT INTO users (id, first_name, middle_name, last_name, second_last_name, phone_number, username, password, enabled, credentials_expired) VALUES(1, 'Jose', 'Alexis', 'Ramirez', 'del Valle', '555555555555', 'jalexisrdv', '$2a$10$Eg7bKj0VW61UjNxbtZtzRuRTw53l7Xwsd/PZuDcnN8MdtNwhF.vZO', false, false);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
