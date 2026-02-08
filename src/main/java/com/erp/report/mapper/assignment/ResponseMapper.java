package com.erp.report.mapper.assignment;

import com.erp.report.dto.response.ResponseDTO;
import com.erp.report.entity.assignment.ResponseEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class ResponseMapper extends AbstractMapper<ResponseDTO, ResponseEntity> {
    @Override
    public ResponseEntity fromDTO(ResponseDTO dto) {
        return ResponseEntity.create(
                dto.id(),
                dto.assignmentId(),
                dto.itemId(),
                dto.status(),
                dto.comment()
        );
    }

    @Override
    public ResponseDTO fromEntity(ResponseEntity entity) {
        return new ResponseDTO(
                entity.getId(),
                entity.getAssignment().getId(),
                entity.getItem().getId(),
                entity.getStatus(),
                entity.getComment()
        );
    }
}
