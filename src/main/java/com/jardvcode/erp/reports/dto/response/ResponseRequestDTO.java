package com.jardvcode.erp.reports.dto.response;

import com.jardvcode.erp.reports.domain.ResponseStatusEnum;
import com.jardvcode.erp.reports.entity.assignment.ResponseEntity;

public record ResponseRequestDTO(
        Long id,
        ResponseStatusEnum status,
        String comment
) {

    public static ResponseRequestDTO fromEntity(ResponseEntity entity) {
        return new ResponseRequestDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getComment()
        );
    }

}
