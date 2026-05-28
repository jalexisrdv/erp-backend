package com.jardvcode.erp.reports.mapper.template;

import com.jardvcode.erp.reports.dto.template.SectionDTO;
import com.jardvcode.erp.reports.entity.template.SectionEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class SectionMapper {

    private final ItemMapper itemMapper;

    public SectionMapper() {
        this.itemMapper = new ItemMapper();
    }

    public SectionEntity fromDTO(SectionDTO dto) {
        return SectionEntity.create(
                dto.id(),
                dto.uuid(),
                dto.templateId(),
                dto.name(),
                itemMapper.fromDTO(dto.items()),
                dto.position()
        );
    }

    public SectionDTO fromEntity(SectionEntity entity) {
        return new SectionDTO(
                entity.getId(),
                entity.getUuid().toString(),
                entity.getTemplate().getId(),
                entity.getName(),
                itemMapper.fromEntity(entity.getItems()),
                entity.getPosition()
        );
    }

    public Set<SectionEntity> fromDTO(List<SectionDTO> dtos) {
        return dtos.stream()
                .map(dto -> fromDTO(dto))
                .collect(Collectors.toSet());
    }

    public List<SectionDTO> fromEntity(Set<SectionEntity> entities) {
        return entities.stream()
                .map(entity -> fromEntity(entity))
                .collect(Collectors.toList());
    }

}
