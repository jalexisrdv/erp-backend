package com.jardvcode.erp.checklists.service.report;

import com.jardvcode.erp.checklists.dto.report.HeaderDTO;
import com.jardvcode.erp.checklists.dto.report.ReportDTO;
import com.jardvcode.erp.checklists.dto.report.ResponseDTO;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentEntity;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentResponseEntity;
import com.jardvcode.erp.checklists.exception.assignment.AssigmentDoesNotExistException;
import com.jardvcode.erp.checklists.repository.assignment.AssigmentRepository;
import com.jardvcode.erp.checklists.repository.assignment.AssignmentResponseRepository;
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

    public ReportDataProvider(AssigmentRepository assigmentRepository) {
        this.assigmentRepository = assigmentRepository;
    }

    public ReportDTO findByAssignmentId(Long assignmentId) {
        try {
            AssignmentEntity assignment = assigmentRepository.findDetailedById(assignmentId)
                    .orElseThrow(() -> new AssigmentDoesNotExistException(DomainErrorType.CONFLICT));

            HeaderDTO header = new HeaderDTO(
                    assignment.getUnitNumber().toString(),
                    assignment.getTemplateName(),
                    assignment.getOperatorFullName(),
                    assignment.getMechanicFullName(),
                    assignment.getMileage(),
                    assignment.getNextService(),
                    assignment.getTimeIn().toString(),
                    assignment.getTimeOut().toString(),
                    assignment.getDate().toString()
            );

            List<ResponseDTO> responseDtos = assignment.getSections().stream()
                    .flatMap(section -> section.getItems().stream()
                            .map(item -> {
                                var response = item.getResponse();

                                return new ResponseDTO(
                                        section.getName(),
                                        item.getLabel(),
                                        response.getStatus() == null ? "" : response.getStatus().toString(),
                                        Optional.ofNullable(response.getComment()).orElse("")
                                );
                            })
                    ).toList();

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
