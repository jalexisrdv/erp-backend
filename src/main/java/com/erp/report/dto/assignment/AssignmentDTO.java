package com.erp.report.dto.assignment;

import com.erp.report.domain.AssignmentStatusEnum;
import com.erp.report.dto.template.TemplateDTO;

public record AssignmentDTO(
        Long id,
        TemplateDTO template,
        Integer unitNumber,
        OperatorDTO operator,
        MechanicDTO mechanic,
        String mileage,
        String nextService,
        String timeIn,
        String timeOut,
        String date,
        AssignmentStatusEnum status
) {
}
