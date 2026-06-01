package com.jardvcode.erp.inventory.service.movement;

import com.jardvcode.erp.authentication.service.AuthenticatedUserProvider;
import com.jardvcode.erp.inventory.dto.MovementFilterDTO;
import com.jardvcode.erp.inventory.entity.MovementEntity;
import com.jardvcode.erp.inventory.repository.InventoryMovementRepository;
import com.jardvcode.erp.shared.domain.PaginationRules;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovementSearcher {

    private final static Logger LOG = LoggerFactory.getLogger(MovementSearcher.class);

    private final InventoryMovementRepository repository;
    private final AuthenticatedUserProvider userProvider;

    public MovementSearcher(InventoryMovementRepository repository, AuthenticatedUserProvider userProvider) {
        this.repository = repository;
        this.userProvider = userProvider;
    }

    public Page<MovementEntity> search(PaginationRequestDTO paginationRequestDTO, MovementFilterDTO filterDTO) {
        try {
            Pageable pageable = PageRequest.of(paginationRequestDTO.page(), PaginationRules.FETCH_SIZE, Sort.by("createdAt").descending());

            Specification<MovementEntity> specification = null;

            if(!userProvider.isAdmin()) {
                specification = MovementSpecification.isUserdId(userProvider.getUserId());
            }

            if(filterDTO.hasArticleId()) {
                specification = MovementSpecification.isArticleId(filterDTO.articleId());
            }

            if(filterDTO.hasStatus()) {
                specification = specification.and(MovementSpecification.isStatus(filterDTO.status()));
            }

            return repository.findAll(specification, pageable);
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
