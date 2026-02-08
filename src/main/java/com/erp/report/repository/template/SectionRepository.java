package com.erp.report.repository.template;

import com.erp.report.entity.template.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<SectionEntity, Long>, JpaSpecificationExecutor {
    Optional<SectionEntity> findByTemplateIdAndName(Long templateId, String name);
    List<SectionEntity> findByTemplateId(Long templateId);
}
