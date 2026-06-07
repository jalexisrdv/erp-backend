package com.jardvcode.erp.users.service;

import com.jardvcode.erp.users.dto.RoleDTO;
import com.jardvcode.erp.users.dto.UserDTO;
import com.jardvcode.erp.users.exception.UserDoesNotExistException;
import com.jardvcode.erp.users.repository.UserRepository;
import com.jardvcode.erp.authorization.entity.role.RoleEntity;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.shared.domain.PaginationRules;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
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
import java.util.Set;
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

    public UserEntity create(UserDTO dto) {
        try {
            if(repository.findByUsername(dto.username()).isPresent()) {
                throw new UsernameAlreadyExistsException(DomainErrorType.CONFLICT);
            }

            UserEntity user = UserEntity.create(
                    dto.id(),
                    dto.firstName(),
                    dto.middleName(),
                    dto.lastName(),
                    dto.secondLastName(),
                    dto.phoneNumber(),
                    dto.username()
            );

            return repository.save(user);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public Page<UserEntity> search(PaginationRequestDTO paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page(), PaginationRules.FETCH_SIZE, Sort.by("id").descending());

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

            return repository.findAll(specification, pageable);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public UserEntity update(UserDTO dto) {
        try {
            UserEntity foundUser = repository.findById(dto.id())
                    .orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

            foundUser.update(
                    dto.firstName(),
                    dto.middleName(),
                    dto.lastName(),
                    dto.secondLastName(),
                    dto.phoneNumber(),
                    dto.username()
            );

            return repository.save(foundUser);
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

    public List<RoleEntity> assignRoles(Long id, List<RoleDTO> roleDtos) {
        try {
            UserEntity foundUser = repository.findById(id)
                    .orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

            Set<RoleEntity> roles = roleDtos.stream()
                            .map(role -> {
                                return RoleEntity.create(
                                        role.id(),
                                        role.name()
                                );
                            })
                            .collect(Collectors.toSet());

            foundUser.assignRoles(roles);

            return repository.save(foundUser).getRoles().stream().toList();
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
            UserEntity user = repository.findById(id)
                    .orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

            return user.getRoles().stream().toList();
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
