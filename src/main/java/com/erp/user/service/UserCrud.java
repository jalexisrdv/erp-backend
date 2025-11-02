package com.erp.user.service;

import com.erp.authentication.exception.UserDoesNotExistException;
import com.erp.authentication.repository.UserRepository;
import com.erp.role.entity.RoleEntity;
import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.HttpCode;
import com.erp.shared.domain.PaginationRules;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.erp.user.entity.UserEntity;
import com.erp.user.exception.UsernameAlreadyExistsException;
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
public final class UserCrud {

    private final static Logger LOG = LoggerFactory.getLogger(UserCrud.class);

    private final UserRepository repository;

    public UserCrud(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity create(UserEntity entity) {
        try {
            if(repository.findByUsername(entity.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException(HttpCode.conflict());
            }

            return repository.save(entity);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public ResponsePaginationDTO<UserEntity> searchByPage(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

            Specification<UserEntity> specification = (root, query, builder) -> {
                String search = "%" + paginationDTO.search().toLowerCase() + "%";
                return builder.or(
                        builder.like(builder.lower(root.get("firstName")), search),
                        builder.like(builder.lower(root.get("middleName")), search),
                        builder.like(builder.lower(root.get("lastName")), search),
                        builder.like(builder.lower(root.get("phoneNumber")), search),
                        builder.like(builder.lower(root.get("username")), search)
                );
            };

            Page<UserEntity> page = repository.findAll(specification, pageable);

            return ResponsePaginationDTO.create(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalPages(),
                    page.getNumberOfElements(),
                    page.getContent()
            );
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public UserEntity update(UserEntity entity) {
        try {
            UserEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new UserDoesNotExistException(HttpCode.conflict()));

            entityFound.setFirstName(entity.getFirstName());
            entityFound.setMiddleName(entity.getMiddleName());
            entityFound.setLastName(entity.getLastName());
            entityFound.setSecondLastName(entity.getSecondLastName());
            entityFound.setPhoneNumber(entity.getPhoneNumber());

            return repository.save(entityFound);
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

    public List<RoleEntity> assignRoles(Long id, List<RoleEntity> roles) {
        try {
            UserEntity entity = repository.findById(id).orElseThrow(() -> new UserDoesNotExistException(HttpCode.conflict()));
            entity.setRoles(roles);

            return repository.save(entity).getRoles();
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<RoleEntity> fetchRoles(Long id) {
        try {
            UserEntity entity = repository.findById(id).orElseThrow(() -> new UserDoesNotExistException(HttpCode.conflict()));

            return entity.getRoles();
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }
}
