package com.jardvcode.erp.reports.mapper.assignment;

import com.jardvcode.erp.reports.dto.response.detail.HeaderDTO;
import com.jardvcode.erp.reports.dto.response.detail.ReportDTO;
import com.jardvcode.erp.reports.dto.response.detail.ResponseDTO;
import com.jardvcode.erp.reports.dto.response.detail.SectionDTO;
import com.jardvcode.erp.reports.entity.assignment.AssignmentEntity;
import com.jardvcode.erp.reports.entity.template.SectionEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public final class ReportResponseMapper {

    public ReportDTO fromEntity(AssignmentEntity assignment) {
        HashMap<String, SectionDTO> sections = new HashMap<>();

        assignment.getResponses().forEach(responseEntity -> {
            SectionEntity sectionEntity = responseEntity.section();

            SectionDTO section = sections.computeIfAbsent(
                    sectionEntity.getName(),
                    key -> new SectionDTO(
                            sectionEntity.getId(),
                            sectionEntity.getName(),
                            new ArrayList<>(),
                            sectionEntity.getPosition()
                    )
            );

            ResponseDTO response = new ResponseDTO(
                    responseEntity.getId(),
                    responseEntity.getItem().getLabel(),
                    responseEntity.getStatus(),
                    responseEntity.getComment(),
                    responseEntity.getItem().getPosition()
            );

            section.responses().add(response);
        });

        HeaderDTO header = new HeaderDTO(
                assignment.getUnitNumber().toString(),
                assignment.getTemplate().getName(),
                assignment.getOperator().fullName(),
                assignment.getMechanic().fullName(),
                assignment.getMileage(),
                assignment.getNextService(),
                assignment.getTimeIn().toString(),
                assignment.getTimeOut().toString(),
                assignment.getDate().toString()
        );

        List<SectionDTO> sortedSections = sections.values().stream()
                .sorted(Comparator.comparingInt(SectionDTO::position))
                .map(section -> new SectionDTO(
                        section.id(),
                        section.name(),
                        section.responses().stream()
                                .sorted(Comparator.comparingInt(ResponseDTO::position))
                                .toList(),
                        section.position()
                ))
                .toList();

        return new ReportDTO(
                header,
                new ArrayList<>(sortedSections)
        );
    }
}
