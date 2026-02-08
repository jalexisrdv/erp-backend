package com.erp.report.service.assignment;

import com.erp.report.entity.assignment.AssignmentEntity;
import com.erp.report.entity.assignment.ResponseEntity;
import com.erp.report.entity.template.ItemEntity;
import com.erp.report.exception.assignment.AssigmentDoesNotExistException;
import com.erp.report.repository.assignment.AssigmentRepository;
import com.erp.report.repository.assignment.ResponseRepository;
import com.erp.report.repository.template.ItemRepository;
import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.PaginationRules;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.erp.user.entity.UserEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
import java.util.stream.Collectors;

@Service
public class AssigmentCrud {

    private final static Logger LOG = LoggerFactory.getLogger(AssigmentCrud.class);

    private final AssigmentRepository assignmentRepository;
    private final ItemRepository itemRepository;
    private final ResponseRepository responseRepository;

    public AssigmentCrud(AssigmentRepository assignmentRepository, ItemRepository itemRepository, ResponseRepository responseRepository) {
        this.assignmentRepository = assignmentRepository;
        this.itemRepository = itemRepository;
        this.responseRepository = responseRepository;
    }

    @Transactional
    public AssignmentEntity create(AssignmentEntity entity) {
        try {
            List<ItemEntity> items = itemRepository.findByTemplateId(entity.getTemplate().getId());

            if(items.isEmpty()) {
                throw new RuntimeException("No puedes asignar una plantilla vacia");
            }

            AssignmentEntity assignment = assignmentRepository.save(entity);

            List<ResponseEntity> responses = items
            .stream().map( item -> {
                return ResponseEntity.create(
                        null,
                        assignment.getId(),
                        item.getId(),
                        null,
                        null
                );
            }).collect(Collectors.toList());

            responseRepository.saveAll(responses);

            return assignment;
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public AssignmentEntity update(AssignmentEntity entity) {
        try {
            AssignmentEntity entityFound = assignmentRepository.findById(entity.getId()).orElseThrow(() -> new AssigmentDoesNotExistException());

            entityFound.update(
                    entity.getUnitNumber(),
                    entity.getOperator().getId(),
                    entity.getMechanic().getId(),
                    entity.getMileage(),
                    entity.getNextService(),
                    entity.getTimeIn(),
                    entity.getTimeOut()
            );

            return assignmentRepository.save(entityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponsePaginationDTO<AssignmentEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<AssignmentEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";

                Join<AssignmentEntity, UserEntity> operator = root.join("operator", JoinType.LEFT);

                return builder.or(
                        builder.like(builder.lower(operator.get("firstName")), search)
                );
            };

            Page<AssignmentEntity> page = assignmentRepository.findAll(specification, pageable);

            return ResponsePaginationDTO.create(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalPages(),
                    page.getTotalElements(),
                    page.getContent()
            );
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public void deleteById(Long id) {
        try {
            assignmentRepository.deleteById(id);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
