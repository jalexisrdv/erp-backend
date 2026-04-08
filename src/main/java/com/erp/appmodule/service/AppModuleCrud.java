package com.erp.appmodule.service;

import com.erp.appmodule.entity.AppModuleEntity;
import com.erp.appmodule.entity.AppModuleViewEntity;
import com.erp.appmodule.exception.AppModuleDoesNotExistException;
import com.erp.appmodule.repository.AppModuleRepository;
import com.erp.appmodule.repository.AppModuleViewRepository;
import com.erp.shared.domain.DomainError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppModuleCrud {

    private final static Logger LOG = LoggerFactory.getLogger(AppModuleCrud.class);

    private final AppModuleRepository repository;
    private final AppModuleViewRepository viewRepository;

    public AppModuleCrud(AppModuleRepository repository, AppModuleViewRepository viewRepository) {
        this.repository = repository;
        this.viewRepository = viewRepository;
    }

    public AppModuleViewEntity create(AppModuleEntity entity) {
        try {
            AppModuleEntity savedEntity = repository.save(entity);

            return viewRepository.findById(savedEntity.getId()).orElseThrow(() -> new AppModuleDoesNotExistException());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public List<AppModuleViewEntity> findByParentIdNotNull() {
        try {
            return viewRepository.findByParentIdNotNull();
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public AppModuleViewEntity update(AppModuleEntity entity) {
        try {
            AppModuleEntity savedEntity = repository.save(entity);

            return viewRepository.findById(savedEntity.getId()).orElseThrow(() -> new AppModuleDoesNotExistException());
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }  catch(Exception e) {
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
