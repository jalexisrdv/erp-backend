package com.jardvcode.erp.checklists.dto.response.detail;

import com.jardvcode.erp.checklists.entity.assignment.AssignmentSectionEntity;

import java.util.List;
import java.util.stream.Collectors;

public record SectionDTO(
        Long id,
        String name,
        List<ResponseDTO> responses,
        Integer position
) {

    public static SectionDTO fromEntity(AssignmentSectionEntity entity) {
        return new SectionDTO(
                entity.getId(),
                entity.getName(),
                entity.getItems().stream().map(ResponseDTO::fromEntity).collect(Collectors.toList()),
                entity.getPosition()
        );
    }

}
