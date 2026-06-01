package com.jardvcode.erp.inventory.service.movement;

import com.jardvcode.erp.authentication.service.AuthenticatedUserProvider;
import com.jardvcode.erp.inventory.dto.ApproveMovementRequestDTO;
import com.jardvcode.erp.inventory.dto.OutputMovementRequestDTO;
import com.jardvcode.erp.inventory.dto.RejectMovementRequestDTO;
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
    private final AuthenticatedUserProvider userProvider;
    private final EntityManager entityManager;

    public OutputMovementService(InventoryRepository inventoryRepository, InventoryMovementRepository movementRepository, AuthenticatedUserProvider userProvider, EntityManager entityManager) {
        this.inventoryRepository = inventoryRepository;
        this.movementRepository = movementRepository;
        this.userProvider = userProvider;
        this.entityManager = entityManager;
    }

    public MovementEntity create(OutputMovementRequestDTO dto) {
        try {
            InventoryEntity foundInventory = inventoryRepository.findById(dto.itemId())
                    .orElseThrow(() -> new ItemDoesNotExistException(DomainErrorType.DEPENDENCY));

            if(!foundInventory.hasStockFor(dto.quantity())) {
                throw new InsufficientStockException();
            }

            MovementEntity movement = MovementEntity.createOutput(
                    dto.itemId(),
                    dto.quantity(),
                    dto.reason(),
                    userProvider.getUserId()
            );

            MovementEntity savedMovement = movementRepository.save(movement);

            entityManager.flush();

            inventoryRepository.increaseReservedOutputCount(dto.itemId(), dto.quantity());

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

    public MovementEntity update(OutputMovementRequestDTO dto) {
        try {
            MovementEntity foundMovement = movementRepository.findById(dto.id())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));

            foundMovement.updateOutput(
                    dto.quantity(),
                    dto.reason(),
                    userProvider.getUserId()
            );

            movementRepository.save(foundMovement);

            entityManager.flush();

            inventoryRepository.increaseReservedOutputCount(dto.itemId(), dto.quantity());

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

    public MovementEntity approve(ApproveMovementRequestDTO dto) {
        try {
            MovementEntity foundMovement = movementRepository.findById(dto.id())
                    .orElseThrow(() -> new MovementDoesNotExistException(DomainErrorType.DEPENDENCY));

            foundMovement.approveOutput(userProvider.getUserId());

            movementRepository.save(foundMovement);

            entityManager.flush();

            inventoryRepository.increaseOutputCount(dto.itemId(), foundMovement.getQuantity());
            inventoryRepository.decreaseReservedOutputCount(dto.itemId(), foundMovement.getQuantity());

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

            foundMovement.reject(userProvider.getUserId(), dto.reason());

            movementRepository.save(foundMovement);

            entityManager.flush();

            inventoryRepository.decreaseReservedOutputCount(dto.itemId(), foundMovement.getQuantity());

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

}
