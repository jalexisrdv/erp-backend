package com.erp.authentication.configuration;

import com.erp.user.entity.UserEntity;
import com.erp.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    public JpaUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findWithRolesAndPermissionsByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
        return UserDetailsImpl.create(user);
    }

}