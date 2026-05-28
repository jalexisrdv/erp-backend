package com.jardvcode.erp.authentication.configuration;

import com.jardvcode.erp.users.entity.UserEntity;
import com.jardvcode.erp.users.repository.UserRepository;
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