package com.erp.role.service;

import com.erp.permission.entity.PermissionEntity;
import com.erp.role.entity.RoleEntity;
import com.erp.role.exception.RoleDoesNotExistException;
import com.erp.role.repository.RoleRepository;
import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.HttpCode;
import com.erp.shared.domain.PaginationRules;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class RoleCrud {

    private final static Logger LOG = LoggerFactory.getLogger(RoleCrud.class);

    private final RoleRepository repository;

    public RoleCrud(RoleRepository repository) {
        this.repository = repository;
    }

    public RoleEntity create(RoleEntity entity) {
        try {
            return repository.save(entity);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<RoleEntity> findAll() {
        try {
            return repository.findAll();
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public ResponsePaginationDTO<RoleEntity> searchByPagination(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<RoleEntity> specification = (root, query, builder) -> {
                return builder.like(builder.lower(root.get("name")), "%" + paginationDTO.search().toLowerCase()  + "%");
            };

            Page<RoleEntity> page = repository.findAll(specification, pageable);

            return ResponsePaginationDTO.create(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalPages(),
                    page.getTotalElements(),
                    page.getContent()
            );
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public RoleEntity update(RoleEntity entity) {
        try {
            return repository.save(entity);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<PermissionEntity> assignPermissions(Long roleId, List<PermissionEntity> permissions) {
        try {
            RoleEntity role = repository.findById(roleId).orElseThrow(() -> new RoleDoesNotExistException(HttpCode.conflict()));
            role.setPermissions(permissions);

            return repository.save(role).getPermissions();
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<PermissionEntity> fetchPermissions(Long roleId) {
        try {
            RoleEntity role = repository.findById(roleId).orElseThrow(() -> new RoleDoesNotExistException(HttpCode.conflict()));

            return role.getPermissions();
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
