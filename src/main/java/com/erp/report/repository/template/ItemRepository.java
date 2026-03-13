package com.erp.report.repository.template;

import com.erp.report.entity.template.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>, JpaSpecificationExecutor {
    Optional<ItemEntity> findBySectionIdAndLabel(Long sectionId, String label);
    List<ItemEntity> findBySectionId(Long sectionId);
}
