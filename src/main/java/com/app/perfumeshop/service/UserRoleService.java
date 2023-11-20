package com.app.perfumeshop.service;

import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserRoleService(UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public UserRole getRole(UserRoleEnum role) {
        return this.userRoleRepository
                .findByUserRole(role)
                .orElseThrow(() ->
                        new UsernameNotFoundException("There is no such role " + role));
        //TODO make a custom message exception
    }
    public void removeRole(Long userId) {

        User user = userRepository.findById(userId).get();

        user.getUserRoles().removeIf(userRole -> userRole.getUserRole().name().equals("EMPLOYEE"));

        userRepository.save(user);
    }
    public void addRole(Long userId) {

        User user = userRepository.findById(userId).get();

        user.getUserRoles().add(userRoleRepository.findByUserRole(UserRoleEnum.EMPLOYEE).get());

        userRepository.save(user);
    }
}
