package com.erp.permission.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission")
    private String name;

    public static PermissionEntity create(Long id, String name) {
        PermissionEntity entity = new PermissionEntity();

        entity.setId(id);
        entity.setName(name);

        return entity;
    }

    public static PermissionEntity withId(Long id) {
        PermissionEntity entity = new PermissionEntity();

        entity.setId(id);

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