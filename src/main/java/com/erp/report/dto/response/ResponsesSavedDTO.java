package com.erp.report.dto.response;

public record ResponsesSavedDTO(
    String message,
    Integer count,
    Long assignmentId
) {
}
