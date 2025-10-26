package com.erp.permission.service;

import com.erp.permission.entity.PermissionEntity;
import com.erp.permission.repository.PermissionRepository;
import com.erp.shared.domain.DomainError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class PermissionCrud {

    private final static Logger LOG = LoggerFactory.getLogger(PermissionCrud.class);

    private final PermissionRepository repository;

    public PermissionCrud(PermissionRepository repository) {
        this.repository = repository;
    }

    public PermissionEntity create(PermissionEntity entity) {
        try {
            return repository.save(entity);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public List<PermissionEntity> findAll() {
        return repository.findAll();
    }

    public List<PermissionEntity> fetchByPage(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("id").ascending());
        Page<PermissionEntity> page = repository.findAll(pageable);
        return page.stream().toList();
    }

    public PermissionEntity update(PermissionEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
