package com.erp.report.service.template;

import com.erp.inventory.exception.inventory.ItemDoesNotExistException;
import com.erp.report.entity.template.ItemEntity;
import com.erp.report.exception.template.item.ItemAlreadyExistsException;
import com.erp.report.repository.template.ItemRepository;
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
public final class ItemCrud {

    private final static Logger LOG = LoggerFactory.getLogger(ItemCrud.class);

    private final ItemRepository repository;

    public ItemCrud(ItemRepository repository) {
        this.repository = repository;
    }

    public ItemEntity create(ItemEntity entity) {
        try {
            if(repository.findBySectionIdAndLabel(entity.getSection().getId(), entity.getLabel()).isPresent()) {
                throw new ItemAlreadyExistsException();
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

    public ItemEntity update(ItemEntity entity) {
        try {
            ItemEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new ItemDoesNotExistException());

            entityFound.update(entity.getLabel());

            return repository.save(entityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponsePaginationDTO<ItemEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<ItemEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("label")), search)
                );
            };

            Page<ItemEntity> page = repository.findAll(specification, pageable);

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

    public List<ItemEntity> findBySectionId(Long templateId) {
        try {
            return repository.findBySectionId(templateId);
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
