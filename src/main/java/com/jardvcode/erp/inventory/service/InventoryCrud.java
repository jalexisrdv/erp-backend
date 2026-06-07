package com.jardvcode.erp.inventory.service;

import com.jardvcode.erp.authentication.service.AuthenticatedUserProvider;
import com.jardvcode.erp.inventory.dto.InventoryDTO;
import com.jardvcode.erp.inventory.entity.InventoryEntity;
import com.jardvcode.erp.inventory.entity.ItemCategoryEntity;
import com.jardvcode.erp.inventory.exception.category.ItemCategoryDoesNotExistException;
import com.jardvcode.erp.inventory.exception.inventory.ItemDoesNotExistException;
import com.jardvcode.erp.inventory.exception.inventory.ItemAlreadyExistsException;
import com.jardvcode.erp.inventory.repository.InventoryRepository;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryCrud {

    private final static Logger LOG = LoggerFactory.getLogger(InventoryCrud.class);

    private final InventoryRepository inventoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final AuthenticatedUserProvider userProvider;

    public InventoryCrud(InventoryRepository repository, ItemCategoryRepository itemCategoryRepository, AuthenticatedUserProvider userProvider) {
        this.inventoryRepository = repository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.userProvider = userProvider;
    }

    public InventoryEntity create(InventoryDTO dto) {
        try {
            ItemCategoryEntity foundItemCategory = itemCategoryRepository.findById(dto.category().id())
                    .orElseThrow(() -> new ItemCategoryDoesNotExistException(DomainErrorType.CONFLICT));

            if(inventoryRepository.findByItemName(dto.name()).isPresent()) {
                throw new ItemAlreadyExistsException(DomainErrorType.CONFLICT);
            }

            InventoryEntity inventory = InventoryEntity.create(
                    dto.id(),
                    foundItemCategory,
                    dto.code(),
                    dto.name(),
                    dto.minimumStock(),
                    userProvider.getUserId()
            );

            return inventoryRepository.save(inventory);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public InventoryEntity update(InventoryDTO dto) {
        try {
            ItemCategoryEntity foundItemCategory = itemCategoryRepository.findById(dto.category().id())
                    .orElseThrow(() -> new ItemCategoryDoesNotExistException(DomainErrorType.CONFLICT));

            InventoryEntity foundInventory = inventoryRepository.findById(dto.id())
                    .orElseThrow(() -> new ItemDoesNotExistException(DomainErrorType.CONFLICT));

            foundInventory.update(
                    foundItemCategory,
                    dto.code(),
                    dto.name(),
                    dto.minimumStock(),
                    userProvider.getUserId()
            );

            return inventoryRepository.save(foundInventory);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<InventoryEntity> search(PaginationRequestDTO dto) {
        try {
            Pageable pageable = PageRequest.of(dto.page(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<InventoryEntity> specification = (root, query, builder) -> {
                String search = "%" + dto.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("itemCode")), search),
                        builder.like(builder.lower(root.get("itemName")), search)
                );
            };

            return inventoryRepository.findAll(specification, pageable);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
