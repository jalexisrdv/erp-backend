package com.erp.report.entity.template;

import jakarta.persistence.*;

@Entity
@Table(name = "report_items")
public final class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    @Column(name = "label")
    private String label;

    public static ItemEntity create(Long id, Long sectionId, String label) {
        SectionEntity section = new SectionEntity();
        section.setId(sectionId);

        ItemEntity entity = new ItemEntity();

        entity.setId(id);
        entity.setSection(section);
        entity.setLabel(label);

        return entity;
    }

    public void update(String label) {
        this.setLabel(label);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
