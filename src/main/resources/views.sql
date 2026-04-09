CREATE OR REPLACE VIEW view_app_modules AS
WITH RECURSIVE module_tree AS (
    SELECT
        module.id,
        module.code,
        module.name,
        module.parent_id,
        CAST(module.name AS TEXT) AS full_path
    FROM
    	app_modules module
    WHERE
    	module.parent_id IS NULL

    UNION ALL

    SELECT
        submodule.id,
        submodule.code,
        submodule.name,
        submodule.parent_id,
        (tree.full_path || ' > ' || submodule.name) AS full_path
    FROM
    	app_modules submodule
    JOIN
    	module_tree tree ON submodule.parent_id = tree.id
)
SELECT
    id,
    code,
    name,
    parent_id,
    full_path
FROM
	module_tree;

CREATE OR REPLACE VIEW view_permissions AS
WITH RECURSIVE module_tree AS (
    SELECT
    	module.id,
    	module.parent_id,
    	module.name,
    	CAST(module.name AS TEXT) AS full_path
    FROM
    	app_modules module
    WHERE
    	module.parent_id IS NULL

    UNION ALL

    SELECT
    	submodule.id,
    	submodule.parent_id,
    	submodule.name,
    	(tree.full_path || ' > ' || submodule.name)
    FROM
    	app_modules submodule
    JOIN
    	module_tree tree ON submodule.parent_id = tree.id
)
SELECT
    permission.id,
    permission.module_id,
    permission.code,
    permission.name,
    permission.description,
    tree.full_path
FROM
	permissions permission
JOIN
	module_tree tree ON permission.module_id = tree.id;

CREATE OR REPLACE VIEW view_role_permissions AS
WITH RECURSIVE module_tree AS (
    SELECT
    	module.id,
    	module.parent_id,
    	module.name,
    	CAST(module.name AS TEXT) AS full_path
    FROM
    	app_modules module
    WHERE
    	module.parent_id IS NULL

    UNION ALL

    SELECT
    	submodule.id,
    	submodule.parent_id,
    	submodule.name,
    	(tree.full_path || ' > ' || submodule.name)
    FROM
    	app_modules submodule
    JOIN
    	module_tree tree ON submodule.parent_id = tree.id
)
SELECT
    permission.id,
    permission.module_id,
    role_permission.role_id,
    permission.code,
    permission.name,
    permission.description,
    tree.full_path
FROM
	permissions permission
JOIN
	module_tree tree ON permission.module_id = tree.id
JOIN
	role_permissions role_permission ON permission.id = role_permission.permission_id;