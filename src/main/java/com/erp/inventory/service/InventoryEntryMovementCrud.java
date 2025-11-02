package com.erp.inventory.service;

import com.erp.authentication.service.AuthenticatedUserProvider;
import com.erp.inventory.domain.StatusEnum;
import com.erp.inventory.entity.InventoryEntryMovementEntity;
import com.erp.inventory.exception.inventory.EntryMovementDoesNotExistException;
import com.erp.inventory.exception.inventory.CannotUpdateReviewedMovementException;
import com.erp.inventory.exception.inventory.StatusAlreadyReviewedException;
import com.erp.inventory.repository.InventoryEntryMovementRepository;
import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.HttpCode;
import com.erp.shared.domain.PaginationRules;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.erp.user.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public final class InventoryEntryMovementCrud {

    private final static Logger LOG = LoggerFactory.getLogger(InventoryEntryMovementCrud.class);

    private final InventoryEntryMovementRepository repository;
    private final AuthenticatedUserProvider userProvider;

    public InventoryEntryMovementCrud(InventoryEntryMovementRepository repository, AuthenticatedUserProvider userProvider) {
        this.repository = repository;
        this.userProvider = userProvider;
    }

    public InventoryEntryMovementEntity create(InventoryEntryMovementEntity entity) {
        try {
            UserEntity user = new UserEntity();
            user.setId(userProvider.getUserId());

            entity.setStatus(StatusEnum.PENDIENTE);
            entity.setCreatedBy(user);
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

    public ResponsePaginationDTO<InventoryEntryMovementEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("createdAt").descending());

            Page<InventoryEntryMovementEntity> page = repository.findAll(pageable);

            return ResponsePaginationDTO.create(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalPages(),
                    page.getNumberOfElements(),
                    page.getContent()
            );
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public InventoryEntryMovementEntity update(InventoryEntryMovementEntity entity) {
        try {
            InventoryEntryMovementEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new EntryMovementDoesNotExistException(HttpCode.conflict()));

            if(entityFound.getStatus() != StatusEnum.PENDIENTE) {
                throw new CannotUpdateReviewedMovementException(HttpCode.conflict());
            }

            UserEntity user = new UserEntity();
            user.setId(userProvider.getUserId());

            entityFound.setQuantity(entity.getQuantity());
            entityFound.setUpdatedBy(user);
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

    public InventoryEntryMovementEntity approve(InventoryEntryMovementEntity entity) {
        try {
            InventoryEntryMovementEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new EntryMovementDoesNotExistException(HttpCode.conflict()));

            if(entityFound.getStatus() != StatusEnum.PENDIENTE) {
                throw new StatusAlreadyReviewedException(HttpCode.conflict());
            }

            UserEntity user = new UserEntity();
            user.setId(userProvider.getUserId());

            entityFound.setStatus(StatusEnum.APROBADO);
            entityFound.setReviewedBy(user);
            entityFound.setReviewedAt(LocalDateTime.now());

            return repository.save(entityFound);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public InventoryEntryMovementEntity reject(InventoryEntryMovementEntity entity) {
        try {
            InventoryEntryMovementEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new EntryMovementDoesNotExistException(HttpCode.conflict()));

            if(entityFound.getStatus() != StatusEnum.PENDIENTE) {
                throw new StatusAlreadyReviewedException(HttpCode.conflict());
            }

            UserEntity user = new UserEntity();
            user.setId(userProvider.getUserId());

            entityFound.setStatus(StatusEnum.RECHAZADO);
            entityFound.setRejectReason(entity.getRejectReason());
            entityFound.setReviewedBy(user);
            entityFound.setReviewedAt(LocalDateTime.now());

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
