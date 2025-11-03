package com.erp.inventory.service;

import com.erp.inventory.entity.ItemCategoryEntity;
import com.erp.inventory.exception.category.ItemCategoryAlreadyExistsException;
import com.erp.inventory.exception.category.ItemCategoryDoesNotExistException;
import com.erp.inventory.repository.ItemCategoryRepository;
import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.HttpCode;
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
public final class ItemCategoryCrud {

    private final static Logger LOG = LoggerFactory.getLogger(ItemCategoryCrud.class);

    private final ItemCategoryRepository repository;

    public ItemCategoryCrud(ItemCategoryRepository repository) {
        this.repository = repository;
    }

    public ItemCategoryEntity create(ItemCategoryEntity entity) {
        try {
            if(repository.findByName(entity.getName()).isPresent()) {
                throw new ItemCategoryAlreadyExistsException(HttpCode.conflict());
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

    public ResponsePaginationDTO<ItemCategoryEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<ItemCategoryEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("name")), search)
                );
            };

            Page<ItemCategoryEntity> page = repository.findAll(specification, pageable);

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

    public ItemCategoryEntity update(ItemCategoryEntity entity) {
        try {
            ItemCategoryEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new ItemCategoryDoesNotExistException(HttpCode.conflict()));

            entityFound.setName(entity.getName());

            return repository.save(entityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
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
