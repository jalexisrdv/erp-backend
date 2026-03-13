package com.erp.report.service.template;

import com.erp.report.entity.template.ItemEntity;
import com.erp.report.entity.template.TemplateEntity;
import com.erp.report.exception.template.TemplateAlreadyExistsException;
import com.erp.report.exception.template.TemplateDoesNotExistException;
import com.erp.report.repository.template.TemplateRepository;
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
public final class TemplateCrud {

    private final static Logger LOG = LoggerFactory.getLogger(TemplateCrud.class);

    private final TemplateRepository repository;

    public TemplateCrud(TemplateRepository repository) {
        this.repository = repository;
    }

    public List<TemplateEntity> findAll() {
        try {
            return repository.findAll();
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public TemplateEntity findWithSectionsAndItemsById(Long id) {
        try {
            return repository.findWithSectionsAndItemsById(id).orElseThrow(() -> new TemplateDoesNotExistException());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public TemplateEntity create(TemplateEntity entity) {
        try {
            if(repository.findByName(entity.getName()).isPresent()) {
                throw new TemplateAlreadyExistsException();
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

    public TemplateEntity update(TemplateEntity entity) {
        try {
            TemplateEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new TemplateDoesNotExistException());

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

    public ResponsePaginationDTO<TemplateEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<TemplateEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("name")), search)
                );
            };

            Page<TemplateEntity> page = repository.findAll(specification, pageable);

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
