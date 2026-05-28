package com.jardvcode.erp.inventory.service.movement;

import com.jardvcode.erp.inventory.dto.OutputMovementRequestDTO;
import com.jardvcode.erp.inventory.entity.InventoryEntity;
import com.jardvcode.erp.inventory.entity.MovementEntity;
import com.jardvcode.erp.inventory.exception.inventory.InsufficientStockException;
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
public class OutputMovementService {

    private final static Logger LOG = LoggerFactory.getLogger(OutputMovementService.class);

    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository movementRepository;
    private final EntityManager entityManager;

    public OutputMovementService(InventoryRepository inventoryRepository, InventoryMovementRepository movementRepository, EntityManager entityManager) {
        this.inventoryRepository = inventoryRepository;
        this.movementRepository = movementRepository;
        this.entityManager = entityManager;
    }

    public MovementEntity create(OutputMovementRequestDTO dto, Long userId) {
        try {
            InventoryEntity inventoryEntity = inventoryRepository.findById(dto.itemId()).orElseThrow(() -> new ItemDoesNotExistException(DomainErrorType.DEPENDENCY));

            if(!inventoryEntity.hasStockFor(dto.quantity())) {
                throw new InsufficientStockException();
            }

            MovementEntity entityCreated = MovementEntity.createOutput(dto.itemId(), dto.quantity(), dto.reason(), userId);

            MovementEntity entitySaved = movementRepository.save(entityCreated);

            entityManager.flush();

            inventoryRepository.increaseReservedOutputCount(dto.itemId(), dto.quantity());

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

    public MovementEntity update(OutputMovementRequestDTO dto, Long userId) {
        try {
            MovementEntity entityFound = movementRepository.findById(dto.id()).orElseThrow(() -> new MovementDoesNotExistException());

            entityFound.updateOutput(dto.quantity(), dto.reason(), userId);

            movementRepository.save(entityFound);

            entityManager.flush();

            inventoryRepository.increaseReservedOutputCount(dto.itemId(), dto.quantity());

            entityManager.clear();

            return movementRepository.findById(dto.id()).orElseThrow(() -> new MovementDoesNotExistException());
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

            entityFound.approveOutput(entity.getReviewedBy().getId());

            movementRepository.save(entityFound);

            entityManager.flush();

            inventoryRepository.increaseOutputCount(entity.getItem().getId(), entityFound.getQuantity());
            inventoryRepository.decreaseReservedOutputCount(entity.getItem().getId(), entityFound.getQuantity());

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

            inventoryRepository.decreaseReservedOutputCount(entity.getItem().getId(), entityFound.getQuantity());

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

}
