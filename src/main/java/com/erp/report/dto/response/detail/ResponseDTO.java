package com.erp.report.dto.response.detail;

public record ResponseDTO(
        Long id,
        String label,
        String status,
        String comment
) {
}
