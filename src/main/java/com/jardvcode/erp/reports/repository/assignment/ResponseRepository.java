package com.jardvcode.erp.reports.repository.assignment;

import com.jardvcode.erp.reports.entity.assignment.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long>, JpaSpecificationExecutor {
    Optional<ResponseEntity> findByAssignmentIdAndItemId(Long assignmentId, Long itemId);
}
