package com.jardvcode.erp.reports.service.template;

import com.jardvcode.erp.reports.entity.template.SectionEntity;
import com.jardvcode.erp.reports.entity.template.TemplateEntity;
import com.jardvcode.erp.reports.exception.template.TemplateAlreadyExistsException;
import com.jardvcode.erp.reports.exception.template.TemplateDoesNotExistException;
import com.jardvcode.erp.reports.exception.template.InvalidTemplateStructureException;
import com.jardvcode.erp.reports.exception.template.section.EmptySectionsException;
import com.jardvcode.erp.reports.repository.template.TemplateRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.PaginationRules;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public TemplateEntity updateStructure(TemplateEntity templateEntity) {
        try {
            TemplateEntity templateEntityFound = repository.findById(templateEntity.getId()).orElseThrow(() -> new TemplateDoesNotExistException());

            Set<SectionEntity> sectionEntities = templateEntity.getSections();

            if(sectionEntities.isEmpty()) {
                throw new EmptySectionsException();
            }

            Map<String, Integer> duplicateSections = new HashMap<>();
            Map<String, Map<String, Integer>> duplicateItems = new HashMap<>();

            sectionEntities.stream().forEach(section -> {
                String sectionName = section.getName().toUpperCase();

                duplicateSections.merge(sectionName, 1, Integer::sum);

                Map<String, Integer> duplicates = section.getItems().stream()
                        .collect(Collectors.toMap(
                                item -> item.getLabel().toUpperCase(),
                                item -> 1,
                                Integer::sum
                        ));

                duplicateItems.put(sectionName, duplicates);
            });

            duplicateSections.entrySet().removeIf(entry -> entry.getValue() <= 1);

            duplicateItems.values().forEach(itemMap ->
                    itemMap.entrySet().removeIf(entry -> entry.getValue() <= 1)
            );

            boolean hasDuplicates = !duplicateSections.isEmpty() ||
                    duplicateItems.values().stream().anyMatch(items -> !items.isEmpty());

            if(hasDuplicates) {
                throw new InvalidTemplateStructureException(duplicateSections, duplicateItems);
            }

            templateEntityFound.updateStructure(sectionEntities);

            return repository.save(templateEntityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
