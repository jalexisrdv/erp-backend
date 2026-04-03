package com.erp.report.mapper.assignment;

import com.erp.report.dto.response.ResponseRequestDTO;
import com.erp.report.dto.response.ResponsesSavedDTO;
import com.erp.report.entity.assignment.ResponseEntity;
import com.erp.shared.mapper.AbstractMapper;

import java.util.List;

public final class ResponseMapper extends AbstractMapper<ResponseRequestDTO, ResponseEntity> {
    @Override
    public ResponseEntity fromDTO(ResponseRequestDTO dto) {
        return ResponseEntity.create(
                dto.id(),
                dto.status(),
                dto.comment()
        );
    }

    @Override
    public ResponseRequestDTO fromEntity(ResponseEntity entity) {
        return new ResponseRequestDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getComment()
        );
    }

    public ResponsesSavedDTO fromEntities(List<ResponseEntity> entities) {
        return new ResponsesSavedDTO(
                "Todas las respuestas se guardaron correctamente.",
                entities.size(),
                entities.get(0).getAssignment().getId()
        );
    }
}
