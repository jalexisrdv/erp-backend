package com.erp.report.repository.template;

import com.erp.report.entity.template.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Long>, JpaSpecificationExecutor {
    Optional<TemplateEntity> findByName(String name);
}
