package com.jardvcode.erp.checklists.service.report;

import com.jardvcode.erp.checklists.dto.report.GeneratedReportDTO;
import com.jardvcode.erp.checklists.dto.report.ReportDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class PdfReportGenerator {

    private final JasperPdfReportGenerator jasperPdfReportGenerator;
    private final ReportDataProvider reportDataProvider;

    public PdfReportGenerator(JasperPdfReportGenerator jasperPdfReportGenerator, ReportDataProvider reportDataProvider) {
        this.jasperPdfReportGenerator = jasperPdfReportGenerator;
        this.reportDataProvider = reportDataProvider;
    }

    public GeneratedReportDTO generate(Long assignmentId) {
        ReportDTO report = reportDataProvider.findByAssignmentId(assignmentId);

        byte[] bytes = jasperPdfReportGenerator.generate(report);

        String filename = "checklist-" + assignmentId + "-" + UUID.randomUUID() + ".pdf";

        return new GeneratedReportDTO(
                filename,
                bytes
        );
    }

}
