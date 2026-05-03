package com.erp.report.entity.template;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "report_items")
public final class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    @Column(name = "label")
    private String label;

    @Column(name = "position")
    private Integer position;

    public static ItemEntity create(Long id, String uuid, String label, Integer position) {
        ItemEntity entity = new ItemEntity();

        entity.id = id;
        entity.uuid = UUID.fromString(uuid);
        entity.label = label;
        entity.position = position;

        return entity;
    }

    public static ItemEntity create(Long id, String uuid, Long sectionId, String label, Integer position) {
        SectionEntity section = new SectionEntity();
        section.setId(sectionId);

        ItemEntity entity = new ItemEntity();

        entity.id = id;
        entity.uuid = UUID.fromString(uuid);
        entity.section = section;
        entity.label = label;
        entity.position = position;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
