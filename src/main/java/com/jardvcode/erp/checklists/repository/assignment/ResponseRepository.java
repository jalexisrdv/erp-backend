package com.jardvcode.erp.checklists.repository.assignment;

import com.jardvcode.erp.checklists.entity.assignment.ResponseEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long>, JpaSpecificationExecutor {
    Optional<ResponseEntity> findByAssignmentIdAndItemId(Long assignmentId, Long itemId);

    @EntityGraph(attributePaths = {"item", "item.section"})
    List<ResponseEntity> findWithSectionAndItemByAssignmentIdOrderByItemPosition(Long assignmentId);
}
