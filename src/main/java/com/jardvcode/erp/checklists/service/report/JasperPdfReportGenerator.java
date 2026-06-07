package com.jardvcode.erp.checklists.service.report;

import com.jardvcode.erp.checklists.dto.report.ReportDTO;
import com.jardvcode.erp.checklists.exception.report.ReportGenerationException;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public final class JasperPdfReportGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(JasperPdfReportGenerator.class);

    public byte[] generate(ReportDTO report) {
        try {
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("LOGO", getClass().getResourceAsStream("/jasper/logo.png"));
            parameters.put("HEADER", report.header().toMap());
            parameters.put("RESPONSES", report.responses());

            InputStream inputStream = getClass().getResourceAsStream("/jasper/checklist.jasper");

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            JRDataSource dataSource = new JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch(JRException  e) {
            LOG.error("Failed to generate checklist report for assignmentId={}", report.assignmentId(), e);
            throw new ReportGenerationException(DomainErrorType.CONFLICT);
        } catch (Exception e) {
            LOG.error("Unexpected error while generating checklist report for assignmentId={}", report.assignmentId(), e);
            throw new ReportGenerationException(DomainErrorType.CONFLICT);
        }
    }

}
