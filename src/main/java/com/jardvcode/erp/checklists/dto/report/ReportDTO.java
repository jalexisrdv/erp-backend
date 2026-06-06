package com.jardvcode.erp.checklists.dto.report;

import java.util.List;

public record ReportDTO(Long assignmentId, HeaderDTO header, List<ResponseDTO> responses) {

}
