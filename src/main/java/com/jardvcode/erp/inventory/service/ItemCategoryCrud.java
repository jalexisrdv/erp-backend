package com.jardvcode.erp.inventory.service;

import com.jardvcode.erp.inventory.dto.ItemCategoryDTO;
import com.jardvcode.erp.inventory.entity.ItemCategoryEntity;
import com.jardvcode.erp.inventory.exception.category.ItemCategoryAlreadyExistsException;
import com.jardvcode.erp.inventory.exception.category.ItemCategoryDoesNotExistException;
import com.jardvcode.erp.inventory.repository.ItemCategoryRepository;
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

@Service
public final class ItemCategoryCrud {

    private final static Logger LOG = LoggerFactory.getLogger(ItemCategoryCrud.class);

    private final ItemCategoryRepository repository;

    public ItemCategoryCrud(ItemCategoryRepository repository) {
        this.repository = repository;
    }

    public ItemCategoryEntity create(ItemCategoryDTO dto) {
        try {
            if(repository.findByName(dto.name()).isPresent()) {
                throw new ItemCategoryAlreadyExistsException(DomainErrorType.CONFLICT);
            }

            ItemCategoryEntity itemCategory = ItemCategoryEntity.create(
              dto.id(),
              dto.name()
            );

            return repository.save(itemCategory);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<ItemCategoryEntity> findAll() {
        try {
            return repository.findAll();
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<ItemCategoryEntity> search(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<ItemCategoryEntity> specification = (root, query, builder) -> {
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

    public ItemCategoryEntity update(ItemCategoryDTO dto) {
        try {
            ItemCategoryEntity foundItemCategory = repository.findById(dto.id()).orElseThrow(() -> new ItemCategoryDoesNotExistException(DomainErrorType.CONFLICT));

            foundItemCategory.update(dto.name());

            return repository.save(foundItemCategory);
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
