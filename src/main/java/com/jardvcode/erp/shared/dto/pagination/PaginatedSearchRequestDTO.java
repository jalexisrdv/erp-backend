package com.jardvcode.erp.shared.dto.pagination;

public record PaginatedSearchRequestDTO<T>(
        String search,
        T filter,
        RequestPageDTO page
) {

    public boolean hasSearch() {
        return search != null && !search.isEmpty();
    }

    public boolean hasFilter() {
        return filter != null;
    }

}
