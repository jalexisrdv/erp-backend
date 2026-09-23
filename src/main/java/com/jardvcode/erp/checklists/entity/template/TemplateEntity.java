package com.jardvcode.erp.checklists.entity.template;

import com.jardvcode.erp.checklists.exception.template.InvalidTemplateStructureException;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "checklist_templates")
public final class TemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private Set<TemplateSectionEntity> sections = new HashSet<>();

    public static TemplateEntity create(Long id, String name) {
        TemplateEntity entity = new TemplateEntity();

        entity.setId(id);
        entity.setName(name);

        return entity;
    }

    public void update(String name) {
        this.name = name;
    }

    public void updateStructure(Set<TemplateSectionEntity> incomingSections) {
        ensureValidStructure(incomingSections);
        updateSections(incomingSections);
    }

    private void ensureValidStructure(Set<TemplateSectionEntity> incomingSections) {
        if(incomingSections.isEmpty()) {
            return;
        }

        Map<String, Integer> duplicatedSectionCounts = new HashMap<>();
        Map<String, Map<String, Integer>> duplicatedItemCountsBySection = new HashMap<>();

        incomingSections.stream().forEach(section -> {
            String sectionName = section.getName().toUpperCase();

            duplicatedSectionCounts.merge(sectionName, 1, Integer::sum);

            Map<String, Integer> duplicatedItemCounts = section.getItems().stream()
                    .collect(Collectors.toMap(
                            item -> item.getLabel().toUpperCase(),
                            item -> 1,
                            Integer::sum
                    ));

            duplicatedItemCountsBySection.put(sectionName, duplicatedItemCounts);
        });

        duplicatedSectionCounts.entrySet().removeIf(duplicatedSectionCount -> duplicatedSectionCount.getValue() <= 1);

        duplicatedItemCountsBySection.values().forEach(itemMap ->
                itemMap.entrySet().removeIf(duplicatedItemCount -> duplicatedItemCount.getValue() <= 1)
        );

        boolean hasDuplicates = !duplicatedSectionCounts.isEmpty() ||
                duplicatedItemCountsBySection.values().stream().anyMatch(items -> !items.isEmpty());

        if(hasDuplicates) {
            throw new InvalidTemplateStructureException(duplicatedSectionCounts, duplicatedItemCountsBySection);
        }
    }

    public void updateSections(Set<TemplateSectionEntity> incomingSections) {
        Map<UUID, TemplateSectionEntity> pendingIncomingSections = incomingSections.stream()
                .collect(Collectors.toMap(TemplateSectionEntity::getUuid, section -> section));

        Iterator<TemplateSectionEntity> currentSectionIterator = this.sections.iterator();

        while(currentSectionIterator.hasNext()) {
            TemplateSectionEntity currentSection = currentSectionIterator.next();

            TemplateSectionEntity incomingSection = pendingIncomingSections.remove(currentSection.getUuid());

            if(incomingSection == null) {
                currentSectionIterator.remove();

                continue;
            }

            currentSection.updateItems(incomingSection.getItems());
            currentSection.update(incomingSection.getName(), incomingSection.getPosition());
        }

        sections.addAll(pendingIncomingSections.values());
    }

    public boolean hasEmptySections() {
        return sections.isEmpty() || sections.stream().anyMatch(s -> s.getItems().isEmpty());
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

    public Set<TemplateSectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<TemplateSectionEntity> sections) {
        this.sections = sections;
    }

}