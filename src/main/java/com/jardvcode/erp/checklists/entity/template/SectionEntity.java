package com.jardvcode.erp.checklists.entity.template;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "checklist_sections")
public final class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private TemplateEntity template;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private Set<ItemEntity> items = new HashSet<>();

    @Column(name = "position")
    private Integer position;

    public static SectionEntity create(Long id, String uuid, Long templateId, String name, Set<ItemEntity> items, Integer position) {
        TemplateEntity template = new TemplateEntity();
        template.setId(templateId);

        SectionEntity entity = new SectionEntity();

        entity.id = id;
        entity.uuid = UUID.fromString(uuid);
        entity.template = template;
        entity.name = name;
        entity.items = items;
        entity.position = position;

        entity.items.forEach(item -> item.setSection(entity));

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public TemplateEntity getTemplate() {
        return template;
    }

    public void setTemplate(TemplateEntity template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEntity)) return false;
        ItemEntity that = (ItemEntity) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
