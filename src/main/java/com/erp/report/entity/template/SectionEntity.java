package com.erp.report.entity.template;

import jakarta.persistence.*;

@Entity
@Table(name = "report_sections")
public final class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "name")
    private String name;

    public static SectionEntity create(Long id, Long templateId, String name) {
        SectionEntity entity = new SectionEntity();

        entity.setId(id);
        entity.setTemplateId(templateId);
        entity.setName(name);

        return entity;
    }

    public void update(String name) {
        this.setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
