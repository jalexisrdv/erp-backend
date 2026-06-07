package com.jardvcode.erp.authorization.service;

import com.jardvcode.erp.authorization.dto.role.PermissionDTO;
import com.jardvcode.erp.authorization.dto.role.RoleDTO;
import com.jardvcode.erp.authorization.entity.role.RoleEntity;
import com.jardvcode.erp.authorization.entity.role.RolePermissionViewEntity;
import com.jardvcode.erp.authorization.exception.RoleDoesNotExistException;
import com.jardvcode.erp.authorization.repository.role.RolePermissionViewRepository;
import com.jardvcode.erp.authorization.repository.role.RoleRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.shared.domain.PaginationRules;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import jakarta.transaction.Transactional;
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
@Transactional
public class RoleCrud {

    private final static Logger LOG = LoggerFactory.getLogger(RoleCrud.class);

    private final RoleRepository repository;
    private final RolePermissionViewRepository rolePermissionViewRepository;

    public RoleCrud(RoleRepository repository, RolePermissionViewRepository rolePermissionViewRepository) {
        this.repository = repository;
        this.rolePermissionViewRepository = rolePermissionViewRepository;
    }

    public RoleEntity create(RoleDTO dto) {
        try {
            RoleEntity role = RoleEntity.create(
                    dto.id(),
                    dto.name()
            );

            return repository.save(role);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public RoleEntity update(RoleDTO dto) {
        try {
            RoleEntity foundRole = repository.findById(dto.id())
                    .orElseThrow(() -> new RoleDoesNotExistException(DomainErrorType.CONFLICT));

            foundRole.update(dto.name());

            return repository.save(foundRole);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
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

    public List<RolePermissionViewEntity> assignPermissions(Long roleId, List<PermissionDTO> dtos) {
        try {
            RoleEntity role = repository.findById(roleId)
                    .orElseThrow(() -> new RoleDoesNotExistException(DomainErrorType.CONFLICT));

            List<Long> permissionIds = dtos.stream().map((permission) -> permission.id()).toList();
            role.assignPermissions(permissionIds);

            repository.save(role);

            return rolePermissionViewRepository.findByRoleId(roleId);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<RolePermissionViewEntity> findPermissionsByRoleId(Long roleId) {
        try {
            return rolePermissionViewRepository.findByRoleId(roleId);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
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

    public Page<RoleEntity> search(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<RoleEntity> specification = (root, query, builder) -> {
                return builder.like(builder.lower(root.get("name")), "%" + paginationDTO.search().toLowerCase()  + "%");
            };

            return repository.findAll(specification, pageable);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
