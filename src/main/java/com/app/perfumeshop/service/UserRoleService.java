package com.app.perfumeshop.service;

import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole getRole(UserRoleEnum role) {
        return this.userRoleRepository
                .findByUserRole(role)
                .orElseThrow(() ->
                        new UsernameNotFoundException("There is no such role " + role));
        //TODO make a custom message exception
    }
}
