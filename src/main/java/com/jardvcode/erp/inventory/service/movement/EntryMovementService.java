package com.jardvcode.erp.inventory.service.movement;

import com.jardvcode.erp.filestorage.service.S3FileStorage;
import com.jardvcode.erp.inventory.dto.EntryMovementRequestDTO;
import com.jardvcode.erp.inventory.entity.MovementEntity;
import com.jardvcode.erp.inventory.exception.inventory.ItemDoesNotExistException;
import com.jardvcode.erp.inventory.exception.movement.MovementDoesNotExistException;
import com.jardvcode.erp.inventory.repository.InventoryMovementRepository;
import com.jardvcode.erp.inventory.repository.InventoryRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EntryMovementService {

    private final static Logger LOG = LoggerFactory.getLogger(EntryMovementService.class);

    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository movementRepository;
    private final EntityManager entityManager;
    private final S3FileStorage fileStorage;

    public EntryMovementService(InventoryRepository inventoryRepository, InventoryMovementRepository movementRepository, EntityManager entityManager, S3FileStorage fileStorage) {
        this.inventoryRepository = inventoryRepository;
        this.movementRepository = movementRepository;
        this.entityManager = entityManager;
        this.fileStorage = fileStorage;
    }

    public MovementEntity create(EntryMovementRequestDTO dto, Long userId) {
        try {
            String invoiceName = dto.invoiceName();
            byte[] invoiceBytes = dto.invoiceBytes();

            MovementEntity.ensureInvoicePdfFormat(invoiceName, invoiceBytes);

            inventoryRepository.findById(dto.itemId()).orElseThrow(() -> new ItemDoesNotExistException(DomainErrorType.DEPENDENCY));

            String invoiceUrl = fileStorage.upload(
                    invoiceBytes,
                    invoiceName,
                    MovementEntity.generateInvoicePath(dto.itemId())
            );

            MovementEntity entityCreated = MovementEntity.createEntry(dto.itemId(), dto.quantity(), invoiceUrl, userId);

            MovementEntity entitySaved = movementRepository.save(entityCreated);

            entityManager.flush();

            inventoryRepository.increasePendingEntryCount(dto.itemId(), dto.quantity());

            entityManager.clear();

            return movementRepository.findById(entitySaved.getId()).orElseThrow(() -> new MovementDoesNotExistException());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public MovementEntity update(EntryMovementRequestDTO dto, Long userId) {
        try {
            String invoiceName = dto.invoiceName();
            byte[] invoiceBytes = dto.invoiceBytes();

            MovementEntity.ensureInvoicePdfFormat(invoiceName, invoiceBytes);

            MovementEntity entityFound = movementRepository.findById(dto.id()).orElseThrow(() -> new MovementDoesNotExistException());

            String invoiceUrl = fileStorage.upload(
                    invoiceBytes,
                    invoiceName,
                    MovementEntity.generateInvoicePath(dto.itemId())
            );

            entityFound.updateEntry(dto.quantity(), invoiceUrl, userId);

            movementRepository.save(entityFound);

            entityManager.flush();

            inventoryRepository.increasePendingEntryCount(dto.itemId(), dto.quantity());

            entityManager.clear();

            return movementRepository.findById(entityFound.getId()).orElseThrow(() -> new MovementDoesNotExistException());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public MovementEntity approve(MovementEntity entity) {
        try {
            MovementEntity entityFound = movementRepository.findById(entity.getId()).orElseThrow(() -> new MovementDoesNotExistException());

            entityFound.approveEntry(entity.getReviewedBy().getId());

            movementRepository.save(entityFound);

            entityManager.flush();

            inventoryRepository.increaseEntryCount(entity.getItem().getId(), entityFound.getQuantity());
            inventoryRepository.decreasePendingEntryCount(entity.getItem().getId(), entityFound.getQuantity());

            entityManager.clear();

            return movementRepository.findById(entity.getId()).orElseThrow(() -> new MovementDoesNotExistException());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public MovementEntity reject(MovementEntity entity) {
        try {
            MovementEntity entityFound = movementRepository.findById(entity.getId()).orElseThrow(() -> new MovementDoesNotExistException());

            entityFound.reject(entity.getReviewedBy().getId(), entity.getRejectReason());

            movementRepository.save(entityFound);

            entityManager.flush();

            inventoryRepository.decreasePendingEntryCount(entity.getItem().getId(), entityFound.getQuantity());

            entityManager.clear();

            return movementRepository.findById(entity.getId()).orElseThrow(() -> new MovementDoesNotExistException());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public String previewInvoice(Long id) {
        try {
            MovementEntity entity = movementRepository.findById(id).orElseThrow(() -> new MovementDoesNotExistException());

            return fileStorage.generateTemporaryUrl(entity.getInvoiceUrl());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
