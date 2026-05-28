package com.jardvcode.erp.reports.mapper.assignment;

import com.jardvcode.erp.reports.dto.response.ResponseRequestDTO;
import com.jardvcode.erp.reports.entity.assignment.ResponseEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ResponseMapper {
    public ResponseEntity fromDTO(ResponseRequestDTO dto) {
        return ResponseEntity.create(
                dto.id(),
                dto.status(),
                dto.comment()
        );
    }

    public ResponseRequestDTO fromEntity(ResponseEntity entity) {
        return new ResponseRequestDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getComment()
        );
    }

    public Set<ResponseEntity> fromDTO(List<ResponseRequestDTO> dtos) {
        return dtos.stream()
                .map(dto -> fromDTO(dto))
                .collect(Collectors.toSet());
    }
}
