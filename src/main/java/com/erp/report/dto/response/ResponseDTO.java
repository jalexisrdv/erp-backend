package com.erp.report.dto.response;

public record ResponseDTO(
        Long id,
        Long assignmentId,
        Long itemId,
        String status,
        String comment
) {
}
