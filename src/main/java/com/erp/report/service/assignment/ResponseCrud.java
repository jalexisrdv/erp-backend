package com.erp.report.service.assignment;

import com.erp.report.entity.assignment.ResponseEntity;
import com.erp.report.exception.assignment.response.ResponseAlreadyExistsException;
import com.erp.report.exception.assignment.response.ResponseDoesNotExistException;
import com.erp.report.repository.assignment.ResponseRepository;
import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.PaginationRules;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ResponseCrud {

    private final static Logger LOG = LoggerFactory.getLogger(ResponseCrud.class);

    private final ResponseRepository repository;

    public ResponseCrud(ResponseRepository repository) {
        this.repository = repository;
    }

    public List<ResponseEntity> findByTemplateId(Long templateId) {
        try {
            List<ResponseEntity> responses = repository.findByTemplateId(templateId);
            return responses;
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponseEntity create(ResponseEntity entity) {
        try {
            if(repository.findByAssignmentIdAndItemId(entity.getAssignment().getId(), entity.getItem().getId()).isPresent()) {
                throw new ResponseAlreadyExistsException();
            }

            return repository.save(entity);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponseEntity update(ResponseEntity entity) {
        try {
            ResponseEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new ResponseDoesNotExistException());

            entityFound.update(
                    entity.getStatus(),
                    entity.getComment()
            );

            return repository.save(entityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponsePaginationDTO<ResponseEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<ResponseEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("status")), search)
                );
            };

            Page<ResponseEntity> page = repository.findAll(specification, pageable);

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
            repository.deleteById(id);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
