package com.erp.shared.dto.pagination;

public record PaginationRequestDTO(
        String search,
        RequestPageDTO page
) {
}
