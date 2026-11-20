INSERT INTO app_modules (code, name, parent_id) VALUES ('USER_MANAGEMENT', 'Administrar Usuarios', NULL);
INSERT INTO app_modules (code, name, parent_id) VALUES ('USERS', 'Usuarios', (SELECT id FROM app_modules WHERE code = 'USER_MANAGEMENT'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('USER_ROLES', 'Roles', (SELECT id FROM app_modules WHERE code = 'USER_MANAGEMENT'));

INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY', 'Inventario', NULL);
INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY_CATEGORIES', 'Categorías', (SELECT id FROM app_modules WHERE code = 'INVENTORY'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY_ITEMS', 'Artículos', (SELECT id FROM app_modules WHERE code = 'INVENTORY'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('INVENTORY_MOVEMENTS', 'Movimientos', (SELECT id FROM app_modules WHERE code = 'INVENTORY'));

INSERT INTO app_modules (code, name, parent_id) VALUES ('CHECKLISTS', 'Listas de Verificación', NULL);
INSERT INTO app_modules (code, name, parent_id) VALUES ('CHECKLIST_TEMPLATES', 'Plantillas', (SELECT id FROM app_modules WHERE code = 'CHECKLISTS'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('CHECKLIST_ASSIGNMENTS', 'Asignaciones', (SELECT id FROM app_modules WHERE code = 'CHECKLISTS'));
INSERT INTO app_modules (code, name, parent_id) VALUES ('CHECKLIST_CHATBOT', 'ChatBot', (SELECT id FROM app_modules WHERE code = 'CHECKLISTS'));

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

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_TEMPLATES'), 'CHECKLIST_TEMPLATES_VIEW', 'Ver Plantillas', 'Permite visualizar el listado de plantillas de reporte');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_TEMPLATES'), 'CHECKLIST_TEMPLATES_CREATE', 'Crear Plantillas', 'Permite registrar nuevas plantillas de inspección');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_TEMPLATES'), 'CHECKLIST_TEMPLATES_EDIT', 'Editar Plantillas', 'Permite modificar la información general de las plantillas');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_TEMPLATES'), 'CHECKLIST_TEMPLATES_DELETE', 'Eliminar Plantillas', 'Permite borrar plantillas');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_TEMPLATES'), 'CHECKLIST_TEMPLATES_CONFIG', 'Configurar Plantillas', 'Permite configurar plantillas');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_TEMPLATES'), 'CHECKLIST_TEMPLATES_PREVIEW', 'Previsualizar Plantilla', 'Permite ver una vista previa del formato final de la plantilla');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_ASSIGNMENTS'), 'CHECKLIST_ASSIGNMENTS_VIEW', 'Ver Asignaciones', 'Permite visualizar la lista de asignaciones de reportes');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_ASSIGNMENTS'), 'CHECKLIST_ASSIGNMENTS_CREATE', 'Crear Asignación', 'Permite asignar plantillas de inspección a usuarios');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_ASSIGNMENTS'), 'CHECKLIST_ASSIGNMENTS_EDIT', 'Editar Asignación', 'Permite modificar los detalles de una asignación existente');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_ASSIGNMENTS'), 'CHECKLIST_ASSIGNMENTS_DELETE', 'Eliminar Asignación', 'Permite borrar asignaciones');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_ASSIGNMENTS'), 'CHECKLIST_ASSIGNMENTS_FILL_REPORT', 'Responder Reporte Asignado', 'Permite completar y enviar la información del reporte asignado');
INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_ASSIGNMENTS'), 'CHECKLIST_ASSIGNMENTS_PREVIEW', 'Previsualizar Reporte Asignado', 'Permite visualizar el reporte finalizado antes o después de ser enviado');

INSERT INTO permissions (module_id, code, name, description) VALUES ((SELECT id FROM app_modules WHERE code = 'CHECKLIST_CHATBOT'), 'CHECKLIST_CHATBOT_USE', 'Usar chatbot de checklists', 'Permite visualizar y consultar las asignaciones de reportes dentro del chatbot');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');

INSERT INTO role_permissions (role_id, permission_id) SELECT 1, id FROM permissions;

--username=jalexisrdv
--password=jalexisrdv
INSERT INTO users (id, first_name, middle_name, last_name, second_last_name, phone_number, username, password, enabled, credentials_expired) VALUES(1, 'Jose', 'Alexis', 'Ramirez', 'del Valle', '555555555555', 'jalexisrdv', '$2a$10$Eg7bKj0VW61UjNxbtZtzRuRTw53l7Xwsd/PZuDcnN8MdtNwhF.vZO', false, false);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

INSERT INTO checklist_templates (name) VALUES ('Formato para servicios A y C (BASICO)');

INSERT INTO checklist_sections (id, template_id, name, position) VALUES
(1, 1, 'SISTEMA DE DIRECCION', 1),
(2, 1, 'SUSPENCION DELANTERA', 2),
(3, 1, 'MOTOR', 3),
(4, 1, 'EMBRAGUE', 4),
(5, 1, 'TRANSMISION', 5),
(6, 1, 'DIFERENCIALES', 6),
(7, 1, 'QUINTARUEDA', 7),
(8, 1, 'SISTEMA ELECTRICO/ELECTRONICO', 8),
(9, 1, 'SISTEMA DE RODAMIENTO', 9),
(10, 1, 'SISTEMA DE FRENOS', 10),
(11, 1, 'EQUIPO DE SEGURIDAD', 11),
(12, 1, 'LIMPIEZA Y LUBRICACION', 12);

INSERT INTO checklist_items (section_id, label, position) VALUES
(1, 'REVISAR NIVEL DE ACEITE', 1),
(2, 'REVISAR ABRAZADERAS', 1),
(3, 'REVISION DE FUGAS (ACEITE, AGUA, DIESEL)', 1),
(3, 'RESET INSITE', 2),
(3, 'CAMBIO DE FILTROS (DIESEL)', 3),
(3, 'REVISAR TENSION DE BANDAS', 4),
(3, 'NIVEL DE REFRIGERANTE', 5),
(3, 'CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS', 6),
(4, 'AJUSTE O CALIBRAR', 1),
(4, 'REVISION LIQUIDO', 2),
(5, 'REVISAR NIVEL DE ACEITE', 1),
(5, 'REVISAR FUGAS DE ACEITE', 2),
(5, 'REVISAR FUGAS DE AIRE', 3),
(6, 'REVISAR NIVEL DE ACEITE', 1),
(6, 'REVISAR FUGAS DE ACEITE', 2),
(7, 'LIMPIEZA GENERAL', 1),
(7, 'LUBRICACION', 2),
(8, 'CHECAR BATERIAS Y TERMINALES', 1),
(8, 'CHECAR ARNESES Y CABLES (SUELTOS)', 2),
(8, 'REVISAR LUCES', 3),
(8, 'REV ALARMA DE REVERSA', 4),
(8, 'REVISAR CODIGOS DE FALLA DE MOTOR EN TABLERO DE INSTRUMENTOS', 5),
(9, 'REVISAR PRESION (100LBS)', 1),
(9, 'REVISAR MARCAJE', 2),
(10, 'REVISAR FUGAS DE AIRE', 1),
(10, 'PURGAR TANQUES DE AIRE', 2),
(10, 'CALIBRAR FRENOS', 3),
(11, 'REVISAR CINTURONES DE SEGURIDAD', 1),
(11, 'REVISAR PORTA EXTINTOR', 2),
(12, 'LAVADO DE MOTOR Y CARROCERIA', 1),
(12, 'ENGRASADO GENERAL', 2);

INSERT INTO checklist_assignments (
    template_id,
    template_name,
    unit_number,
    operator_user_id,
    operator_full_name,
    mechanic_user_id,
    mechanic_full_name,
    mileage,
    next_service,
    time_in,
    time_out,
    date,
    status
) VALUES (
    1,
    'Formato para servicios A y C (BASICO)',
    243,
    1,
    'PEDRO OCELOT',
    1,
    'CATARINO',
    '1299961',
    '1,300,000 BASICO',
    '09:00:00',
    '18:00:00',
    CURRENT_DATE,
    'PENDING'
);

INSERT INTO assignment_sections (section_id, assignment_id, name, position, status)
SELECT id, 1, name, position, 'PENDING'
FROM checklist_sections
WHERE template_id = 1
ORDER BY position;

INSERT INTO assignment_items (item_id, section_id, label, position)
SELECT items.id, sections.id, items.label, items.position
FROM checklist_items items
JOIN assignment_sections sections ON sections.id = items.section_id
WHERE sections.assignment_id = 1
ORDER BY sections.position, items.position;

INSERT INTO assignment_responses (item_id, status, comment)
SELECT
    items.id,
    NULL,
    NULL
FROM assignment_items items;