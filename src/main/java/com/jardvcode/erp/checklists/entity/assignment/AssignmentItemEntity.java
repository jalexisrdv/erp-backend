package com.jardvcode.erp.checklists.entity.assignment;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "assignment_items")
public final class AssignmentItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private AssignmentSectionEntity section;

    @Column(name = "label")
    private String label;

    @Column(name = "position")
    private Integer position;

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

    public AssignmentSectionEntity getSection() {
        return section;
    }

    public void setSection(AssignmentSectionEntity section) {
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
