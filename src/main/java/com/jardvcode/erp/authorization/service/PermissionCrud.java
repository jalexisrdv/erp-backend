package com.jardvcode.erp.authorization.service;

import com.jardvcode.erp.authorization.dto.permission.PermissionRequestDTO;
import com.jardvcode.erp.authorization.entity.permission.PermissionEntity;
import com.jardvcode.erp.authorization.entity.permission.PermissionViewEntity;
import com.jardvcode.erp.authorization.exception.PermissionDoesNotExistException;
import com.jardvcode.erp.authorization.repository.permission.PermissionRepository;
import com.jardvcode.erp.authorization.repository.permission.PermissionViewRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PermissionCrud {

    private final static Logger LOG = LoggerFactory.getLogger(PermissionCrud.class);

    private final PermissionRepository repository;
    private final PermissionViewRepository viewRepository;

    public PermissionCrud(PermissionRepository repository, PermissionViewRepository viewRepository) {
        this.repository = repository;
        this.viewRepository = viewRepository;
    }

    public PermissionViewEntity create(PermissionRequestDTO dto) {
        try {
            PermissionEntity permission = PermissionEntity.create(
                    dto.id(),
                    dto.moduleId(),
                    dto.code(),
                    dto.name(),
                    dto.description()
            );

            PermissionEntity savedPermission = repository.save(permission);

            return viewRepository.findById(savedPermission.getId())
                    .orElseThrow(() -> new PermissionDoesNotExistException(DomainErrorType.CONFLICT));
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public List<PermissionViewEntity> findByModuleId(Long id) {
        try {
            return viewRepository.findByModuleId(id);
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public PermissionViewEntity update(PermissionRequestDTO dto) {
        try {
            PermissionEntity foundPermission = repository.findById(dto.id())
                    .orElseThrow(() -> new PermissionDoesNotExistException(DomainErrorType.CONFLICT));

            foundPermission.update(
                    dto.moduleId(),
                    dto.code(),
                    dto.name(),
                    dto.description()
            );

            repository.save(foundPermission);

            return viewRepository.findById(dto.id())
                    .orElseThrow(() -> new PermissionDoesNotExistException(DomainErrorType.CONFLICT));
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

}
