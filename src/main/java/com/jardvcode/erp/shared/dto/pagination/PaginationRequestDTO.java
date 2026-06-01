package com.jardvcode.erp.shared.dto.pagination;

public record PaginationRequestDTO(
        String search,
        Integer page
) {
}
