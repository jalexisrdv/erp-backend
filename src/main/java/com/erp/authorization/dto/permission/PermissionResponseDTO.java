package com.erp.authorization.dto.permission;

public record PermissionResponseDTO(
        Long id,
        String code,
        String name,
        String description,
        String fullPath
) {
}
