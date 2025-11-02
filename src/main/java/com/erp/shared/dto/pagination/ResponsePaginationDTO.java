package com.erp.shared.dto.pagination;

import java.util.List;

public record ResponsePaginationDTO<E>(
        ResponsePageDTO page,
        List<E> data
) {
    public static <E> ResponsePaginationDTO create(Integer pageNumber, Integer pageSize, Integer totalPages, Integer totalElements, List<E> data) {
        return new ResponsePaginationDTO(
                new ResponsePageDTO(
                        pageNumber,
                        pageSize,
                        totalPages,
                        totalElements
                ),
                data
        );
    }
}
