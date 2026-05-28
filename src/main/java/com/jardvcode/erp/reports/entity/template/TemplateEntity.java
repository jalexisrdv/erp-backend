package com.jardvcode.erp.reports.entity.template;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "report_templates")
public final class TemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private Set<SectionEntity> sections = new HashSet<>();

    public static TemplateEntity create(Long id, String name) {
        TemplateEntity entity = new TemplateEntity();

        entity.setId(id);
        entity.setName(name);

        return entity;
    }

    public static TemplateEntity create(Long id, Set<SectionEntity> sections) {
        TemplateEntity entity = new TemplateEntity();
        entity.id = id;
        entity.sections = sections;

        entity.sections.forEach(section -> {
            section.setTemplate(entity);
        });

        return entity;
    }

    public void update(String name) {
        this.setName(name);
    }

    public void updateStructure(Set<SectionEntity> sections) {
        this.sections.clear();
        this.sections.addAll(sections);
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

    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionEntity)) return false;
        SectionEntity that = (SectionEntity) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}