package com.erp.report.repository.template;

import com.erp.report.entity.template.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>, JpaSpecificationExecutor {
    Optional<ItemEntity> findBySectionIdAndLabel(Long sectionId, String label);
    List<ItemEntity> findBySectionId(Long sectionId);


    @Query("""
        SELECT item
        FROM ItemEntity item
        INNER JOIN FETCH item.section section
        WHERE section.templateId = :templateId
    """)
    List<ItemEntity> findByTemplateId(Long templateId);
}
