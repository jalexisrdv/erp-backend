package com.jardvcode.erp.checklists.controller.report;

import com.jardvcode.erp.checklists.dto.report.GeneratedReportDTO;
import com.jardvcode.erp.checklists.service.report.PdfReportGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "checklists/reports")
public final class ChecklistReportController {

    private final PdfReportGenerator reportGenerator;

    public ChecklistReportController(PdfReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping(value = "/{assignmentId}")
    public ResponseEntity<byte[]> generate(@PathVariable Long assignmentId) {
        GeneratedReportDTO generatedReportDTO = reportGenerator.generate(assignmentId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + generatedReportDTO.filename() + "\"")
                .body(generatedReportDTO.bytes());
    }

}
