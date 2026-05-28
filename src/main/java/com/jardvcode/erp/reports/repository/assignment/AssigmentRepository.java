package com.jardvcode.erp.reports.repository.assignment;

import com.jardvcode.erp.reports.entity.assignment.AssignmentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AssigmentRepository extends JpaRepository<AssignmentEntity, Long>, JpaSpecificationExecutor {
    @EntityGraph(attributePaths = {"template", "responses"})
    Optional<AssignmentEntity> findWithTemplateAndResponsesById(Long id);

    @EntityGraph(attributePaths = {"template", "operator", "mechanic"})
    Optional<AssignmentEntity> findWithTemplateAndOperatorAndMechanicById(Long id);
}
