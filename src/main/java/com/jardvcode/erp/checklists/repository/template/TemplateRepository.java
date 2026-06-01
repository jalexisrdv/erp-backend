package com.jardvcode.erp.checklists.repository.template;

import com.jardvcode.erp.checklists.entity.template.TemplateEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Long>, JpaSpecificationExecutor {
    Optional<TemplateEntity> findByName(String name);

    @EntityGraph(attributePaths = {"sections", "sections.items"})
    Optional<TemplateEntity> findWithSectionsAndItemsById(Long id);
}
