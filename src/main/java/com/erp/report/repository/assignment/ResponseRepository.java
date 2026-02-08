package com.erp.report.repository.assignment;

import com.erp.report.entity.assignment.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long>, JpaSpecificationExecutor {
    Optional<ResponseEntity> findByAssignmentIdAndItemId(Long assignmentId, Long itemId);

    @Query("""
        SELECT response
        FROM ResponseEntity response
        INNER JOIN FETCH response.assignment assignment
        INNER JOIN FETCH assignment.template template
        INNER JOIN FETCH response.item item
        INNER JOIN FETCH item.section section
        WHERE section.templateId = :templateId
    """)
    List<ResponseEntity> findByTemplateId(Long templateId);
}
