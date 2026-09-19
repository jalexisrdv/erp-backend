package com.jardvcode.erp.checklists.repository.assignment;

import com.jardvcode.erp.checklists.entity.assignment.AssignmentSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AssignmentSectionRepository extends JpaRepository<AssignmentSectionEntity, Long>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            INSERT INTO assignment_sections(section_id, uuid, assignment_id, name, position)
            SELECT
                templateSections.id, templateSections.uuid, :assignmentId, templateSections.name, templateSections.position
            FROM
                checklist_sections templateSections
            WHERE
                templateSections.template_id = :templateId
            """)
    void snapshot(Long templateId, Long assignmentId);

}
