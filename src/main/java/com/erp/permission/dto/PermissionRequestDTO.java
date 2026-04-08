package com.erp.permission.dto;

public record PermissionRequestDTO(
        Long id,
        Long moduleId,
        String code,
        String name,
        String description
) {
}
