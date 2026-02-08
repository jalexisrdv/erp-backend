package com.erp.report.repository.assignment;

import com.erp.report.entity.assignment.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssigmentRepository extends JpaRepository<AssignmentEntity, Long>, JpaSpecificationExecutor {

}
