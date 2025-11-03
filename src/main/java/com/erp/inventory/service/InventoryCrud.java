package com.erp.inventory.service;

import com.erp.authentication.service.AuthenticatedUserProvider;
import com.erp.inventory.entity.InventoryEntity;
import com.erp.inventory.exception.inventory.ItemDoesNotExistException;
import com.erp.inventory.exception.inventory.ItemAlreadyExistsException;
import com.erp.inventory.repository.InventoryRepository;
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

import java.time.LocalDateTime;

@Service
public final class InventoryCrud {

    private final static Logger LOG = LoggerFactory.getLogger(InventoryCrud.class);

    private final InventoryRepository repository;
    private final AuthenticatedUserProvider userProvider;

    public InventoryCrud(InventoryRepository repository, AuthenticatedUserProvider userProvider) {
        this.repository = repository;
        this.userProvider = userProvider;
    }

    public InventoryEntity create(InventoryEntity entity) {
        try {
            if(repository.findByItemName(entity.getItemName()).isPresent()) {
                throw new ItemAlreadyExistsException(HttpCode.conflict());
            }

            entity.setCreatedBy(userProvider.getUserId());
            entity.setCreatedAt(LocalDateTime.now());

            return repository.save(entity);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponsePaginationDTO<InventoryEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<InventoryEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("itemCode")), search),
                        builder.like(builder.lower(root.get("itemName")), search)
                );
            };

            Page<InventoryEntity> page = repository.findAll(specification, pageable);

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

    public InventoryEntity update(InventoryEntity entity) {
        try {
            InventoryEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new ItemDoesNotExistException(HttpCode.conflict()));

            entityFound.setItemCategoryId(entity.getItemCategoryId());
            entityFound.setItemCode(entity.getItemCode());
            entityFound.setItemName(entity.getItemName());
            entityFound.setMinimumStock(entity.getMinimumStock());
            entityFound.setUpdatedBy(userProvider.getUserId());
            entityFound.setUpdatedAt(LocalDateTime.now());

            return repository.save(entityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
