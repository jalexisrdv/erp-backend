package com.erp.role.dto;

public record PermissionDTO(
        Long id,
        Long moduleId,
        String code,
        String name,
        String description,
        String fullPath
) {
}
