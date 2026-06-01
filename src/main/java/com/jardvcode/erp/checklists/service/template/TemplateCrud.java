package com.jardvcode.erp.checklists.service.template;

import com.jardvcode.erp.checklists.dto.template.TemplateDTO;
import com.jardvcode.erp.checklists.dto.template.TemplateStructureRequestDTO;
import com.jardvcode.erp.checklists.entity.template.ItemEntity;
import com.jardvcode.erp.checklists.entity.template.SectionEntity;
import com.jardvcode.erp.checklists.entity.template.TemplateEntity;
import com.jardvcode.erp.checklists.exception.template.TemplateAlreadyExistsException;
import com.jardvcode.erp.checklists.exception.template.TemplateDoesNotExistException;
import com.jardvcode.erp.checklists.repository.template.TemplateRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.shared.domain.PaginationRules;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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
            return repository.findWithSectionsAndItemsById(id)
                    .orElseThrow(() -> new TemplateDoesNotExistException(DomainErrorType.DEPENDENCY));
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public TemplateEntity create(TemplateDTO dto) {
        try {
            if(repository.findByName(dto.name()).isPresent()) {
                throw new TemplateAlreadyExistsException(DomainErrorType.DEPENDENCY);
            }

            TemplateEntity template = TemplateEntity.create(
                    dto.id(),
                    dto.name()
            );

            return repository.save(template);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public TemplateEntity update(TemplateDTO dto) {
        try {
            TemplateEntity foundTemplate = repository.findById(dto.id())
                    .orElseThrow(() -> new TemplateDoesNotExistException(DomainErrorType.DEPENDENCY));

            foundTemplate.update(dto.name());

            return repository.save(foundTemplate);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<TemplateEntity> search(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<TemplateEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("name")), search)
                );
            };

            return repository.findAll(specification, pageable);
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

    public void updateStructure(TemplateStructureRequestDTO dto) {
        try {
            TemplateEntity foundTemplate = repository.findById(dto.id())
                    .orElseThrow(() -> new TemplateDoesNotExistException(DomainErrorType.DEPENDENCY));

            Set<SectionEntity> sections = dto.sections().stream()
                    .map((section -> {
                        Set<ItemEntity> items = section.items().stream()
                                .map(item -> {
                                    return ItemEntity.create(
                                            item.id(),
                                            item.uuid(),
                                            item.label(),
                                            item.position()
                                    );
                                })
                                .collect(Collectors.toSet());

                        return SectionEntity.create(
                                section.id(),
                                section.uuid(),
                                section.templateId(),
                                section.name(),
                                items,
                                section.position()
                        );
                    }))
                    .collect(Collectors.toSet());

            foundTemplate.updateStructure(sections);

            repository.save(foundTemplate);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
