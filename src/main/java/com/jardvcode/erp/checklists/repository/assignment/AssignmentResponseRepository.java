package com.jardvcode.erp.checklists.repository.assignment;

import com.jardvcode.erp.checklists.entity.assignment.AssignmentResponseEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssignmentResponseRepository extends JpaRepository<AssignmentResponseEntity, Long>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            INSERT INTO assignment_responses(assignment_id, item_id)
            SELECT
            	:assignmentId, items.id
            FROM
            	assignment_items items
            	INNER JOIN assignment_sections sections ON items.section_id = sections.id
            WHERE
            	sections.assignment_id = :assignmentId
            """)
    void createDefaultResponsesFromItems(Long assignmentId);

    @EntityGraph(attributePaths = {"item", "item.section"})
    List<AssignmentResponseEntity> findWithSectionAndItemByAssignmentIdOrderByItemPosition(Long assignmentId);

}
