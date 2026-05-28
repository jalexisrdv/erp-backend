package com.jardvcode.erp.appmodules.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_modules")
public class AppModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    public static AppModuleEntity create(Long id, String code, String name, Long parentId) {
        AppModuleEntity entity = new AppModuleEntity();

        entity.id = id;
        entity.code = code;
        entity.name = name;
        entity.parentId = parentId;

        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
