package com.erp.role.dto;

public record RoleFilterDTO(
        String name
) {

    public boolean hasName() {
        return name != null && !name.isBlank();
    }

}
