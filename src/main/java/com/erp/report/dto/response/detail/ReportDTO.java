package com.erp.report.dto.response.detail;

import java.util.List;

public record ReportDTO(HeaderDTO header, List<SectionDTO> sections) {

}