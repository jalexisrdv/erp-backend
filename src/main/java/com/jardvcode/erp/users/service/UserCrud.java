package com.jardvcode.erp.users.service;

import com.jardvcode.erp.users.exception.UserDoesNotExistException;
import com.jardvcode.erp.users.repository.UserRepository;
import com.jardvcode.erp.authorization.entity.role.RoleEntity;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.shared.domain.PaginationRules;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.jardvcode.erp.users.entity.UserEntity;
import com.jardvcode.erp.users.exception.UsernameAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class UserCrud {

    private final static Logger LOG = LoggerFactory.getLogger(UserCrud.class);

    private final UserRepository repository;

    public UserCrud(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> findAll() {
        try {
            return repository.findAll();
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public UserEntity create(UserEntity entity) {
        try {
            if(repository.findByUsername(entity.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException();
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
                    page.getTotalElements(),
                    page.getContent()
            );
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public UserEntity update(UserEntity entity) {
        try {
            UserEntity entityFound = repository.findById(entity.getId()).orElseThrow(() -> new UserDoesNotExistException());

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
            UserEntity entity = repository.findById(id).orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.DEPENDENCY));
            entity.setRoles(roles.stream().collect(Collectors.toSet()));

            return repository.save(entity).getRoles().stream().toList();
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
            UserEntity entity = repository.findById(id).orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.DEPENDENCY));

            return entity.getRoles().stream().toList();
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
