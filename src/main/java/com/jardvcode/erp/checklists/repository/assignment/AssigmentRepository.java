package com.jardvcode.erp.checklists.repository.assignment;

import com.jardvcode.erp.checklists.entity.assignment.AssignmentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AssigmentRepository extends JpaRepository<AssignmentEntity, Long>, JpaSpecificationExecutor {

    @EntityGraph(attributePaths = {"sections", "sections.items", "sections.items.response"})
    Optional<AssignmentEntity> findDetailedById(Long id);

}
