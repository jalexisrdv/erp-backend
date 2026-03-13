package com.erp.report.mapper.template;

import com.erp.report.dto.response.detail.HeaderDTO;
import com.erp.report.dto.template.preview.ItemDTO;
import com.erp.report.dto.template.preview.SectionDTO;
import com.erp.report.dto.template.preview.TemplatePreviewDTO;
import com.erp.report.entity.template.TemplateEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class TemplatePreviewMapper {
    public TemplatePreviewDTO fromEntity(TemplateEntity entity) {
        List<SectionDTO> sections = entity.getSections().stream().map(section -> {
            List<ItemDTO> items = section.getItems().stream().map(item -> {
                return new ItemDTO(item.getId(), item.getLabel());
            }).collect(Collectors.toList());

            return new SectionDTO(section.getId(), section.getName(), items);
        }).collect(Collectors.toList());

        return new TemplatePreviewDTO(
                new HeaderDTO(
                        "-",
                        entity.getName(),
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-"
                ),
                sections
        );
    }
}
