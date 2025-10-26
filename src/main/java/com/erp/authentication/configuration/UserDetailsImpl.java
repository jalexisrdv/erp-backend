package com.erp.authentication.configuration;

import java.util.*;

import com.erp.permission.entity.PermissionEntity;
import com.erp.role.entity.RoleEntity;
import com.erp.user.entity.UserEntity;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails, CredentialsContainer {

    private final Long id;
    private final String username;
    private String password;
    private final Set<String> roles;
    private final Set<String> permissions;
    private final Collection<? extends GrantedAuthority> authorities;

    private UserDetailsImpl(Long id, String username, String password, Set<String> roles, Set<String> permissions,
                            Set<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.authorities = authorities;
    }

    public static UserDetailsImpl create(UserEntity user) {
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (RoleEntity role : user.getRoles()) {
            roles.add(role.getName());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

            for (PermissionEntity permission : role.getPermissions()) {
                permissions.add(permission.getName());
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                roles,
                permissions,
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

}