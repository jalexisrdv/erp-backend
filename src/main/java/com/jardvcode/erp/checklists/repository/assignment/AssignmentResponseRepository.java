package com.jardvcode.erp.checklists.repository.assignment;

import com.jardvcode.erp.checklists.entity.assignment.AssignmentResponseEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

public interface AssignmentResponseRepository extends JpaRepository<AssignmentResponseEntity, Long>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            INSERT INTO assignment_responses(item_id)
            SELECT
            	items.id
            FROM
            	assignment_items items
            	INNER JOIN assignment_sections sections ON items.section_id = sections.id
            WHERE
            	sections.assignment_id = :assignmentId
            """)
    void createDefaultResponsesFromItems(Long assignmentId);

}
