package com.erp.authentication.service;

import com.erp.authentication.configuration.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public final class AuthenticatedUserProvider {

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Long getUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl user) {
            return user.getId();
        }
        return null;
    }

    public boolean hasRole(String role) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }

    public boolean hasPermission(String permission) {
        return hasRole(permission);
    }

}
