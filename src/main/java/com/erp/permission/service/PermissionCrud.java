package com.erp.permission.service;

import com.erp.permission.entity.PermissionEntity;
import com.erp.permission.entity.PermissionViewEntity;
import com.erp.permission.exception.PermissionDoesNotExistException;
import com.erp.permission.repository.PermissionRepository;
import com.erp.permission.repository.PermissionViewRepository;
import com.erp.shared.domain.DomainError;
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

    public PermissionViewEntity create(PermissionEntity entity) {
        try {
            PermissionEntity savedEntity = repository.save(entity);

            return viewRepository.findById(savedEntity.getId()).orElseThrow(() -> new PermissionDoesNotExistException());
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

    public PermissionViewEntity update(PermissionEntity entity) {
        try {
            PermissionEntity savedEntity = repository.save(entity);

            return viewRepository.findById(savedEntity.getId()).orElseThrow(() -> new PermissionDoesNotExistException());
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
