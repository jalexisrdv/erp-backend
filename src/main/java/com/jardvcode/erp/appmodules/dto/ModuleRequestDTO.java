package com.jardvcode.erp.appmodules.dto;

public record ModuleRequestDTO (
        Long id,
        String code,
        String name,
        Long parentId
) {
}