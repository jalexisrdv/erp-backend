package com.jardvcode.erp.authorization.dto.permission;

public record PermissionRequestDTO(
        Long id,
        Long moduleId,
        String code,
        String name,
        String description
) {

}
