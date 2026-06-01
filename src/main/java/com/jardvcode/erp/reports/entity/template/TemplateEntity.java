package com.jardvcode.erp.reports.entity.template;

import com.jardvcode.erp.reports.exception.template.InvalidTemplateStructureException;
import com.jardvcode.erp.reports.exception.template.section.EmptySectionsException;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        this.name = name;
    }

    public void updateStructure(Set<SectionEntity> sections) {
        if(sections.isEmpty()) {
            throw new EmptySectionsException();
        }

        Map<String, Integer> duplicatedSectionCounts = new HashMap<>();
        Map<String, Map<String, Integer>> duplicatedItemCountsBySection = new HashMap<>();

        sections.stream().forEach(section -> {
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
        
        this.sections.clear();
        this.sections.addAll(sections);
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

    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

}