package com.jardvcode.erp.shared.dto.pagination;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record ResponsePaginationDTO<R>(
        ResponsePageDTO page,
        List<R> data
) {
    public static <E> ResponsePaginationDTO create(Integer pageNumber, Integer pageSize, Integer totalPages, Long totalElements, List<E> data) {
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

    public static <E, R> ResponsePaginationDTO<R> create(Page<E> page, Function<E, R> mapper) {
        List<R> mappedContent = page.getContent().stream()
                .map(mapper)
                .toList();

        return new ResponsePaginationDTO<>(
                new ResponsePageDTO(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalPages(),
                        page.getTotalElements()
                ),
                mappedContent
        );
    }
}
