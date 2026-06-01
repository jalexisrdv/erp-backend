package com.jardvcode.erp.inventory.service.movement;

import com.jardvcode.erp.authentication.service.AuthenticatedUserProvider;
import com.jardvcode.erp.filestorage.service.S3FileStorage;
import com.jardvcode.erp.inventory.dto.ApproveMovementRequestDTO;
import com.jardvcode.erp.inventory.dto.EntryMovementRequestDTO;
import com.jardvcode.erp.inventory.dto.RejectMovementRequestDTO;
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
    private final AuthenticatedUserProvider userProvider;
    private final EntityManager entityManager;
    private final S3FileStorage fileStorage;

    public EntryMovementService(InventoryRepository inventoryRepository, InventoryMovementRepository movementRepository, AuthenticatedUserProvider userProvider, EntityManager entityManager, S3FileStorage fileStorage) {
        this.inventoryRepository = inventoryRepository;
        this.movementRepository = movementRepository;
        this.userProvider = userProvider;
        this.entityManager = entityManager;
        this.fileStorage = fileStorage;
    }

    public MovementEntity create(EntryMovementRequestDTO dto) {
        try {
            String invoiceName = dto.invoiceName();
            byte[] invoiceBytes = dto.invoiceBytes();

            MovementEntity.ensureInvoicePdfFormat(invoiceName, invoiceBytes);

            inventoryRepository.findById(dto.itemId())
                    .orElseThrow(() -> new ItemDoesNotExistException(DomainErrorType.DEPENDENCY));

            String invoiceUrl = fileStorage.upload(
                    invoiceBytes,
                    invoiceName,
                    MovementEntity.generateInvoicePath(dto.itemId())
            );

            MovementEntity movement = MovementEntity.createEntry(
                    dto.itemId(),
                    dto.quantity(),
                    invoiceUrl,
                    userProvider.getUserId()
            );

            MovementEntity savedMovement = movementRepository.save(movement);

            entityManager.flush();

            inventoryRepository.increasePendingEntryCount(dto.itemId(), dto.quantity());

            entityManager.clear();

            return movementRepository.findById(savedMovement.getId())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public MovementEntity update(EntryMovementRequestDTO dto) {
        try {
            String invoiceName = dto.invoiceName();
            byte[] invoiceBytes = dto.invoiceBytes();

            MovementEntity.ensureInvoicePdfFormat(invoiceName, invoiceBytes);

            MovementEntity foundMovement = movementRepository.findById(dto.id())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));

            String invoiceUrl = fileStorage.upload(
                    invoiceBytes,
                    invoiceName,
                    MovementEntity.generateInvoicePath(dto.itemId())
            );

            foundMovement.updateEntry(
                    dto.quantity(),
                    invoiceUrl,
                    userProvider.getUserId()
            );

            movementRepository.save(foundMovement);

            entityManager.flush();

            inventoryRepository.increasePendingEntryCount(dto.itemId(), dto.quantity());

            entityManager.clear();

            return movementRepository.findById(foundMovement.getId())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public MovementEntity approve(ApproveMovementRequestDTO dto) {
        try {
            MovementEntity foundMovement = movementRepository.findById(dto.id())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));

            foundMovement.approveEntry(userProvider.getUserId());

            movementRepository.save(foundMovement);

            entityManager.flush();

            inventoryRepository.increaseEntryCount(dto.itemId(), foundMovement.getQuantity());
            inventoryRepository.decreasePendingEntryCount(dto.itemId(), foundMovement.getQuantity());

            entityManager.clear();

            return movementRepository.findById(dto.id())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public MovementEntity reject(RejectMovementRequestDTO dto) {
        try {
            MovementEntity foundMovement = movementRepository.findById(dto.id())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));

            foundMovement.reject(
                    userProvider.getUserId(),
                    dto.reason()
            );

            movementRepository.save(foundMovement);

            entityManager.flush();

            inventoryRepository.decreasePendingEntryCount(dto.itemId(), foundMovement.getQuantity());

            entityManager.clear();

            return movementRepository.findById(dto.id())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));
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
            MovementEntity movement = movementRepository.findById(id)
                    .orElseThrow(() -> new MovementDoesNotExistException());

            return fileStorage.generateTemporaryUrl(movement.getInvoiceUrl());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
