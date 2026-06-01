package com.jardvcode.erp.checklists.dto.response;

import com.jardvcode.erp.checklists.domain.ResponseStatusEnum;
import com.jardvcode.erp.checklists.entity.assignment.ResponseEntity;

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
