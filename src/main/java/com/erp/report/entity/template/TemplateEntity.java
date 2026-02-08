package com.erp.report.entity.template;

import jakarta.persistence.*;

@Entity
@Table(name = "report_templates")
public final class TemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public static TemplateEntity create(Long id, String name) {
        TemplateEntity entity = new TemplateEntity();

        entity.setId(id);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}