package com.erp.authorization.dto.role;

public record PermissionDTO(
        Long id,
        Long moduleId,
        String code,
        String name,
        String description,
        String fullPath
) {
}
