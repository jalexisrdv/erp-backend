package com.erp.shared.dto.pagination;

public record ResponsePageDTO(
        Integer number,
        Integer size,
        Integer pages,
        Long items
) {
}