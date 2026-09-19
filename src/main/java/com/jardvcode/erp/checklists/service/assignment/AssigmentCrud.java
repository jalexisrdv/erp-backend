package com.jardvcode.erp.checklists.service.assignment;

import com.jardvcode.erp.checklists.dto.assignment.AssignmentDTO;
import com.jardvcode.erp.checklists.dto.response.ResponseRequestDTO;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentEntity;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentResponseEntity;
import com.jardvcode.erp.checklists.entity.template.TemplateEntity;
import com.jardvcode.erp.checklists.exception.assignment.AssigmentDoesNotExistException;
import com.jardvcode.erp.checklists.exception.assignment.IncompleteTemplateException;
import com.jardvcode.erp.checklists.exception.assignment.UserDoesNotExistException;
import com.jardvcode.erp.checklists.exception.template.TemplateDoesNotExistException;
import com.jardvcode.erp.checklists.repository.assignment.AssigmentRepository;
import com.jardvcode.erp.checklists.repository.template.TemplateRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.shared.domain.PaginationRules;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.users.entity.UserEntity;
import com.jardvcode.erp.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssigmentCrud {

    private final static Logger LOG = LoggerFactory.getLogger(AssigmentCrud.class);

    private final AssigmentRepository assignmentRepository;
    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final ChecklistAssignmentInitializer checklistAssignmentInitializer;

    public AssigmentCrud(AssigmentRepository assignmentRepository, TemplateRepository templateRepository, UserRepository userRepository, ChecklistAssignmentInitializer checklistAssignmentInitializer) {
        this.assignmentRepository = assignmentRepository;
        this.templateRepository = templateRepository;
        this.userRepository = userRepository;
        this.checklistAssignmentInitializer = checklistAssignmentInitializer;
    }

    public AssignmentEntity create(AssignmentDTO dto) {
        try {
            TemplateEntity template = templateRepository.findWithSectionsAndItemsById(dto.template().id())
                    .orElseThrow(() -> new TemplateDoesNotExistException(DomainErrorType.CONFLICT));

            UserEntity operator = userRepository.findById(dto.operator().id())
                    .orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

            UserEntity mechanic = userRepository.findById(dto.mechanic().id())
                    .orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

            if(template.hasEmptySections()) {
                throw new IncompleteTemplateException(DomainErrorType.CONFLICT);
            }

            AssignmentEntity assignment = AssignmentEntity.create(
                    dto.id(),
                    template.getId(),
                    template.getName(),
                    dto.unitNumber(),
                    operator.getId(),
                    operator.fullName(),
                    mechanic.getId(),
                    mechanic.fullName(),
                    dto.mileage(),
                    dto.nextService(),
                    dto.timeIn(),
                    dto.timeOut()
            );

            assignmentRepository.save(assignment);
            checklistAssignmentInitializer.initialize(template.getId(), assignment.getId());

            return assignment;
        } catch (DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public AssignmentEntity findWithResponsesById(Long id) {
        try {
            return assignmentRepository.findWithResponsesById(id)
                    .orElseThrow(() -> new AssigmentDoesNotExistException(DomainErrorType.CONFLICT));
        } catch (DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public AssignmentEntity update(AssignmentDTO dto) {
        try {
            AssignmentEntity foundAssignment = assignmentRepository.findById(dto.id())
                    .orElseThrow(() -> new AssigmentDoesNotExistException(DomainErrorType.CONFLICT));

            UserEntity operator = userRepository.findById(dto.operator().id())
                    .orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

            UserEntity mechanic = userRepository.findById(dto.mechanic().id())
                    .orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

            foundAssignment.update(
                    dto.unitNumber(),
                    operator.fullName(),
                    mechanic.fullName(),
                    dto.mileage(),
                    dto.nextService(),
                    dto.timeIn(),
                    dto.timeOut()
            );

            assignmentRepository.save(foundAssignment);

            return foundAssignment;
        } catch (DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<AssignmentEntity> search(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<AssignmentEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";

                return builder.or(
                        builder.like(builder.lower(root.get("operatorFullName")), search)
                );
            };

            return assignmentRepository.findAll(specification, pageable);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public void deleteById(Long id) {
        try {
            assignmentRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public void updateResponses(Long assignmentId, List<ResponseRequestDTO> responseDtos) {
        try {
            AssignmentEntity foundAssignment = assignmentRepository.findWithResponsesById(assignmentId)
                    .orElseThrow(() -> new AssigmentDoesNotExistException(DomainErrorType.CONFLICT));

            List<AssignmentResponseEntity> incomingResponses = responseDtos.stream()
                            .map(response -> {
                                return AssignmentResponseEntity.create(
                                        response.id(),
                                        response.status(),
                                        response.comment()
                                );
                            })
                            .toList();

            foundAssignment.updateResponses(incomingResponses);

            assignmentRepository.save(foundAssignment);
        } catch (DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
