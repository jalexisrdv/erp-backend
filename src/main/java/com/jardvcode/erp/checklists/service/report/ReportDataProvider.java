package com.jardvcode.erp.checklists.service.report;

import com.jardvcode.erp.checklists.dto.report.HeaderDTO;
import com.jardvcode.erp.checklists.dto.report.ReportDTO;
import com.jardvcode.erp.checklists.dto.report.ResponseDTO;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentEntity;
import com.jardvcode.erp.checklists.entity.assignment.ResponseEntity;
import com.jardvcode.erp.checklists.exception.assignment.AssigmentDoesNotExistException;
import com.jardvcode.erp.checklists.repository.assignment.AssigmentRepository;
import com.jardvcode.erp.checklists.repository.assignment.ResponseRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.shared.exeption.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class ReportDataProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDataProvider.class);

    private final AssigmentRepository assigmentRepository;
    private final ResponseRepository responseRepository;

    public ReportDataProvider(AssigmentRepository assigmentRepository, ResponseRepository responseRepository) {
        this.assigmentRepository = assigmentRepository;
        this.responseRepository = responseRepository;
    }

    public ReportDTO findByAssignmentId(Long assignmentId) {
        try {
            AssignmentEntity assignment = assigmentRepository.findWithTemplateAndOperatorAndMechanicById(assignmentId)
                    .orElseThrow(() -> new AssigmentDoesNotExistException(DomainErrorType.CONFLICT));

            List<ResponseEntity> responses = responseRepository.findWithSectionAndItemByAssignmentIdOrderByItemPosition(assignmentId);

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

            List<ResponseDTO> responseDtos = responses.stream().map((response) -> {
                return new ResponseDTO(
                        response.getItem().getSection().getName(),
                        response.getItem().getLabel(),
                        response.getStatus() == null ? "" : response.getStatus().toString(),
                        Optional.ofNullable(response.getComment()).orElse("")
                );
            }).collect(Collectors.toList());

            return new ReportDTO(assignmentId, header, responseDtos);
        } catch(DomainError e) {
            LOGGER.error("Checklist report data not found for assignmentId={}", assignmentId, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving checklist report for assignmentId={}", assignmentId, e);
            throw new UnexpectedException();
        }
    }

}
