package com.erp.inventory.service.movement;

import com.erp.authentication.service.AuthenticatedUserProvider;
import com.erp.inventory.dto.MovementFilterDTO;
import com.erp.inventory.entity.MovementEntity;
import com.erp.inventory.repository.InventoryMovementRepository;
import com.erp.shared.domain.PaginationRules;
import com.erp.shared.domain.Roles;
import com.erp.shared.dto.pagination.PaginatedSearchRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MovementSearcher {

    private final static Logger LOG = LoggerFactory.getLogger(MovementSearcher.class);

    private final InventoryMovementRepository repository;
    private final AuthenticatedUserProvider userProvider;

    public MovementSearcher(InventoryMovementRepository repository, AuthenticatedUserProvider userProvider) {
        this.repository = repository;
        this.userProvider = userProvider;
    }

    public ResponsePaginationDTO<MovementEntity> searchByPage(PaginatedSearchRequestDTO<MovementFilterDTO> paginationDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationDTO.page().number(), PaginationRules.FETCH_SIZE, Sort.by("createdAt").descending());

            Specification<MovementEntity> specification = null;

            if(!userProvider.isAdmin()) {
                specification = MovementSpecification.isUserdId(userProvider.getUserId());
            }

            if(paginationDTO.hasFilter() && paginationDTO.filter().hasArticleId()) {
                specification = MovementSpecification.isArticleId(paginationDTO.filter().articleId());
            }

            if(paginationDTO.hasFilter() && paginationDTO.filter().hasStatus()) {
                specification = specification.and(MovementSpecification.isStatus(paginationDTO.filter().status()));
            }

            Page<MovementEntity> page = repository.findAll(specification, pageable);

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

    private class MovementSpecification {

        public static Specification<MovementEntity> isUserdId(Long userId) {
            return (root, query, builder) -> {
                return builder.equal(root.get("createdBy").get("id"), userId);
            };
        }

        public static Specification<MovementEntity> isArticleId(Long value) {
            return (root, query, builder) -> {
                return builder.equal(root.get("item").get("id"), value);
            };
        }

        public static Specification<MovementEntity> isStatus(String value) {
            return (root, query, builder) -> {
                return builder.equal(builder.lower(root.get("status")), value.toLowerCase());
            };
        }

    }

}
