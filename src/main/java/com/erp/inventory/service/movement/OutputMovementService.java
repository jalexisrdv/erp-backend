package com.erp.inventory.service.movement;

import com.erp.authentication.service.AuthenticatedUserProvider;
import com.erp.inventory.entity.InventoryEntity;
import com.erp.inventory.entity.MovementEntity;
import com.erp.inventory.exception.inventory.ItemDoesNotExistException;
import com.erp.inventory.exception.movement.MovementDoesNotExistException;
import com.erp.inventory.repository.InventoryMovementRepository;
import com.erp.inventory.repository.InventoryRepository;
import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;
import com.erp.user.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class OutputMovementService {

    private final static Logger LOG = LoggerFactory.getLogger(OutputMovementService.class);

    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository movementRepository;
    private final AuthenticatedUserProvider userProvider;

    public OutputMovementService(InventoryRepository inventoryRepository, InventoryMovementRepository movementRepository, AuthenticatedUserProvider userProvider) {
        this.inventoryRepository = inventoryRepository;
        this.movementRepository = movementRepository;
        this.userProvider = userProvider;
    }

    public MovementEntity create(MovementEntity entity) {
        try {
            InventoryEntity inventory = inventoryRepository.findById(entity.getItem().getId()).orElseThrow(() -> new ItemDoesNotExistException(DomainErrorType.DEPENDENCY));

            UserEntity user = UserEntity.from(userProvider.getUserId(), userProvider.getUsername());
            MovementEntity entityCreated = entity.createOutput(user, inventory, entity.getQuantity(), entity.getOutputReason());
            entityCreated = movementRepository.save(entityCreated);

            inventoryRepository.updateReservedOutputCount(entity.getItem().getId());
            inventory.increaseReserveOutputCount(entity.getQuantity());

            return entityCreated;
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public MovementEntity update(MovementEntity entity) {
        try {
            MovementEntity entityFound = movementRepository.findById(entity.getId()).orElseThrow(() -> new MovementDoesNotExistException());

            UserEntity user = UserEntity.from(userProvider.getUserId(), userProvider.getUsername());
            entityFound.updateOutput(user, entity.getQuantity(), entity.getOutputReason());
            MovementEntity entitySaved = movementRepository.save(entityFound);

            inventoryRepository.updateReservedOutputCount(entity.getItem().getId());
            entityFound.getItem().increaseReserveOutputCount(entity.getQuantity());

            return entitySaved;
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

            UserEntity user = UserEntity.from(userProvider.getUserId(), userProvider.getUsername());
            entityFound.approveOutput(user);
            MovementEntity entitySaved = movementRepository.save(entityFound);

            inventoryRepository.updateReservedOutputCount(entityFound.getItem().getId());
            inventoryRepository.updateOutputCount(entityFound.getItem().getId());

            return entitySaved;
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

            UserEntity user = UserEntity.from(userProvider.getUserId(), userProvider.getUsername());
            entityFound.reject(user, entity.getRejectReason());
            MovementEntity entitySaved = movementRepository.save(entityFound);

            inventoryRepository.updateReservedOutputCount(entity.getItem().getId());

            return entitySaved;
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
