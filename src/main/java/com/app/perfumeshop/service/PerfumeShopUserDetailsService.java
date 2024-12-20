package com.app.perfumeshop.service;

import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PerfumeShopUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public PerfumeShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository
                .findUserByEmail(email)
                .map(PerfumeShopUserDetailsService::mapUser)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email " + email + " was not found!"));
    }

    private static UserDetails mapUser(User user) {

        return new PerfumeShopUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserRoles().stream()
                        .map(PerfumeShopUserDetailsService::mapAuthorities)
                        .toList()
        );
    }

    private static GrantedAuthority mapAuthorities(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getUserRole());
    }
}
