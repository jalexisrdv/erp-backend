package com.jardvcode.erp.users.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jardvcode.erp.authorization.entity.role.RoleEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "credentials_expired")
    private Boolean credentialsExpired = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public static UserEntity create(Long id, String firstName, String middleName, String lastName, String secondLastName, String phoneNumber, String username) {
        UserEntity entity = new UserEntity();

        entity.id = id;
        entity.firstName = firstName;
        entity.middleName = middleName;
        entity.lastName = lastName;
        entity.secondLastName = secondLastName;
        entity.phoneNumber = phoneNumber;
        entity.username = username;
        entity.password = null;
        entity.enabled = false;

        return entity;
    }

    public void update(String firstName, String middleName, String lastName, String secondLastName, String phoneNumber, String username) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    public void assignRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void resetPassword(String password) {
        this.password = password;
        this.credentialsExpired = true;
    }

    public void changePassword(String password) {
        this.password = password;
        this.credentialsExpired = false;
    }

    public String fullName() {
        return firstName + " " + middleName + " " + lastName + " "  + secondLastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}