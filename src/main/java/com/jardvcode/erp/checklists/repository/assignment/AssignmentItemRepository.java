package com.jardvcode.erp.checklists.repository.assignment;

import com.jardvcode.erp.checklists.entity.assignment.AssignmentItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AssignmentItemRepository extends JpaRepository<AssignmentItemEntity, Long>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            INSERT INTO assignment_items(item_id, uuid, section_id, label, position)
            SELECT
            	templateItems.id, templateItems.uuid, assignmentSections.id, templateItems.label, templateItems.position
            FROM
            	checklist_items templateItems
            	INNER JOIN checklist_sections templateSections ON templateItems.section_id = templateSections.id
            	INNER JOIN assignment_sections assignmentSections ON assignmentSections.section_id = templateSections.id
            WHERE
            	templateSections.template_id = :templateId AND assignmentSections.assignment_id = :assignmentId
            """)
    void snapshot(Long templateId, Long assignmentId);

}
