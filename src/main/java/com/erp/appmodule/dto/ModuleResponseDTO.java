package com.erp.appmodule.dto;

public record ModuleResponseDTO(
        Long id,
        String code,
        String name,
        Long parentId,
        String fullPath
) {
}