package com.jardvcode.erp.checklists.entity.assignment;

import com.jardvcode.erp.checklists.domain.SectionStatusEnum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "assignment_sections")
public final class AssignmentSectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private AssignmentEntity assignment;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private Set<AssignmentItemEntity> items = new HashSet<>();

    @Column(name = "position")
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SectionStatusEnum status = SectionStatusEnum.PENDING;

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

    public AssignmentEntity getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentEntity assignment) {
        this.assignment = assignment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AssignmentItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<AssignmentItemEntity> items) {
        this.items = items;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public SectionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SectionStatusEnum status) {
        this.status = status;
    }

}
