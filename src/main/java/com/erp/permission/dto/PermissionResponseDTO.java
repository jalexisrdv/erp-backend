package com.erp.permission.dto;

public record PermissionResponseDTO(
        Long id,
        String code,
        String name,
        String description,
        String fullPath
) {
}
