--username=example@email.com
--password=pass

INSERT INTO users (username, [password], enabled) 
VALUES ('example@email.com', '$2b$12$/f2bwFkp1Eqz8WFB.30M9O/r8.N5pLYKTQBtXtj8d1oe0XiFcZL12', 1);

INSERT INTO authorities (username, authority) 
VALUES ('example@email.com', 'ROLE_USER');

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

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'ROLES_VIEW', 'Ver Roles', 'Permite visualizar la lista de roles del sistema');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'ROLES_CREATE', 'Crear Roles', 'Permite registrar nuevos roles');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'ROLES_EDIT', 'Editar Roles', 'Permite modificar el nombre de los roles existentes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'ROLES_CONFIG', 'Configurar Permisos', 'Permite asignar o quitar permisos específicos a un rol');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'USER_ROLES'), 'ROLES_DELETE', 'Eliminar Roles', 'Permite borrar roles del sistema');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'CATEGORIES_VIEW', 'Ver Categorías', 'Permite visualizar la lista de categorías');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'CATEGORIES_CREATE', 'Crear Categorías', 'Permite registrar nuevas categorías de inventario');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'CATEGORIES_EDIT', 'Editar Categorías', 'Permite modificar el nombre o detalles de las categorías');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_CATEGORIES'), 'CATEGORIES_DELETE', 'Eliminar Categorías', 'Permite borrar categorías de inventario');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'ITEMS_VIEW', 'Ver Artículos', 'Permite visualizar el catálogo de artículos');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'ITEMS_CREATE', 'Crear Artículos', 'Permite registrar nuevos artículos en el catálogo');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'ITEMS_EDIT', 'Editar Artículos', 'Permite modificar la información técnica o descriptiva de los artículos');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'ITEMS_STOCK_IN', 'Agregar Entradas', 'Permite registrar ingresos de mercancía o incrementos de stock');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_ITEMS'), 'ITEMS_STOCK_OUT', 'Agregar Salidas', 'Permite registrar egresos de mercancía o disminuciones de stock');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_MOVEMENTS'), 'MOVEMENTS_VIEW', 'Ver Movimientos', 'Permite visualizar el historial y estado de los movimientos');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_MOVEMENTS'), 'MOVEMENTS_APPROVE', 'Aprobar Movimientos', 'Permite autorizar movimientos de inventario pendientes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'INVENTORY_MOVEMENTS'), 'MOVEMENTS_REJECT', 'Rechazar Movimientos', 'Permite denegar movimientos de inventario pendientes');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'TEMPLATES_VIEW', 'Ver Plantillas', 'Permite visualizar el listado de plantillas de reporte');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'TEMPLATES_CREATE', 'Crear Plantillas', 'Permite registrar nuevas plantillas de inspección');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'TEMPLATES_EDIT', 'Editar Plantillas', 'Permite modificar la información general de las plantillas');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'TEMPLATES_CONFIG_SECTIONS', 'Configurar Secciones', 'Permite gestionar las secciones dentro de una plantilla');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'TEMPLATES_CONFIG_POINTS', 'Configurar Puntos de Inspección', 'Permite definir los puntos específicos a evaluar en la plantilla');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_TEMPLATES'), 'TEMPLATES_PREVIEW', 'Previsualizar Plantilla', 'Permite ver una vista previa del formato final de la plantilla');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'ASSIGNMENTS_VIEW', 'Ver Asignaciones', 'Permite visualizar la lista de asignaciones de reportes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'ASSIGNMENTS_CREATE', 'Crear Asignación', 'Permite asignar plantillas de inspección a usuarios');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'ASSIGNMENTS_EDIT', 'Editar Asignación', 'Permite modificar los detalles de una asignación existente');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'ASSIGNMENTS_FILL_REPORT', 'Responder Reporte Asignado', 'Permite completar y enviar la información del reporte asignado');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'REPORT_ASSIGNMENTS'), 'ASSIGNMENTS_PREVIEW', 'Previsualizar Reporte Asignado', 'Permite visualizar el reporte finalizado antes o después de ser enviado');
