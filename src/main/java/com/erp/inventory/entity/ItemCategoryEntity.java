package com.erp.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_category")
public final class ItemCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public static ItemCategoryEntity create(Long id, String name) {
        ItemCategoryEntity entity = new ItemCategoryEntity();

        entity.setId(id);
        entity.setName(name);

        return entity;
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
