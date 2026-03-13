package com.erp.report.service.template;

import com.erp.report.entity.template.SectionEntity;
import com.erp.report.exception.template.section.SectionAlreadyExistsException;
import com.erp.report.exception.template.section.SectionDoesNotExistException;
import com.erp.report.repository.template.SectionRepository;
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
public final class SectionCrud {

    private final static Logger LOG = LoggerFactory.getLogger(SectionCrud.class);

    private final SectionRepository repository;

    public SectionCrud(SectionRepository repository) {
        this.repository = repository;
    }

    public SectionEntity create(SectionEntity entity) {
        try {
            if(repository.findByTemplateIdAndName(entity.getTemplate().getId(), entity.getName()).isPresent()) {
                throw new SectionAlreadyExistsException();
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

    public SectionEntity update(SectionEntity entity) {
        try {
            SectionEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new SectionDoesNotExistException());

            entityFound.update(entity.getName());

            return repository.save(entityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponsePaginationDTO<SectionEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<SectionEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("name")), search)
                );
            };

            Page<SectionEntity> page = repository.findAll(specification, pageable);

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


    public List<SectionEntity> findByTemplateId(Long templateId) {
        try {
            return repository.findByTemplateId(templateId);
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
