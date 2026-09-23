package com.jardvcode.erp.checklists.entity.template;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "checklist_sections")
public final class TemplateSectionEntity {

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
    private Set<TemplateItemEntity> items = new HashSet<>();

    @Column(name = "position")
    private Integer position;

    public static TemplateSectionEntity create(Long id, String uuid, Long templateId, String name, Set<TemplateItemEntity> items, Integer position) {
        TemplateEntity template = new TemplateEntity();
        template.setId(templateId);

        TemplateSectionEntity entity = new TemplateSectionEntity();

        entity.id = id;
        entity.uuid = UUID.fromString(uuid);
        entity.template = template;
        entity.name = name;
        entity.items = items;
        entity.position = position;

        entity.items.forEach(item -> item.setSection(entity));

        return entity;
    }

    public void update(String name, Integer position) {
        this.name = name;
        this.position = position;
    }

    public void updateItems(Collection<TemplateItemEntity> incomingItems) {
        Map<UUID, TemplateItemEntity> pendingIncomingItems = incomingItems.stream()
                .collect(Collectors.toMap(TemplateItemEntity::getUuid, item -> item));

        Iterator<TemplateItemEntity> currentItemIterator = items.iterator();

        while(currentItemIterator.hasNext()) {
            TemplateItemEntity currentItem = currentItemIterator.next();

            TemplateItemEntity incomingItem = pendingIncomingItems.remove(currentItem.getUuid());

            if(incomingItem == null) {
                currentItemIterator.remove();

                continue;
            }

            currentItem.update(incomingItem.getLabel(), incomingItem.getPosition());
        }

        items.addAll(pendingIncomingItems.values());
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

    public Set<TemplateItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<TemplateItemEntity> items) {
        this.items = items;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
