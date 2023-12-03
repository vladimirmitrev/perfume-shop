package com.app.perfumeshop.service;

import com.app.perfumeshop.exception.UserNotFoundException;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerfumeShopUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepo;

    @Mock
    private Collection<GrantedAuthority> authorities;
    private PerfumeShopUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new PerfumeShopUserDetailsService(
                mockUserRepo
        );
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // arrange
        User testUser =
                new User()
                        .setUsername("user1")
                        .setEmail("user@example.com")
                        .setPassword("password")
                        .setFirstName("User")
                        .setLastName("Userov")
                        .setPhoneNumber("0888123456")
                        .setUserRoles(
                                List.of(
                                        new UserRole().setUserRole(UserRoleEnum.ADMIN),
                                        new UserRole().setUserRole(UserRoleEnum.EMPLOYEE),
                                        new UserRole().setUserRole(UserRoleEnum.USER)
                                )
                        );

        when(mockUserRepo.findUserByEmail(testUser.getEmail())).
                thenReturn(Optional.of(testUser));

        // act
        PerfumeShopUserDetails userDetails = (PerfumeShopUserDetails)
                toTest.loadUserByUsername(testUser.getEmail());

        // assert
        Assertions.assertEquals(testUser.getEmail(),
                userDetails.getUsername());
        Assertions.assertEquals(testUser.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(),
                userDetails.getFullName());


        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Assertions.assertEquals(3, authorities.size());

        Iterator<? extends GrantedAuthority> authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                authoritiesIter.next().getAuthority());
        Assertions.assertEquals("ROLE_" + UserRoleEnum.EMPLOYEE.name(),
                authoritiesIter.next().getAuthority());
        Assertions.assertEquals("ROLE_" + UserRoleEnum.USER.name(),
                authoritiesIter.next().getAuthority());
    }
    @Test
    void testLoadUserByUsername_UserDoesNotExist() {

        // arrange
        // NOTE: No need to arrange anything, mocks return empty optionals.

        // act && assert
        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existant@example.com")
        );
    }
    @Test
    public void testIsCredentialsNonExpired() {

        PerfumeShopUserDetails userDetails = new PerfumeShopUserDetails(
                1L, "password", "username", "John", "Doe", authorities);

        boolean isCredentialsNonExpired = userDetails.isCredentialsNonExpired();

        assertTrue(isCredentialsNonExpired);
    }
    @Test
    public void testIsAccountNonExpired() {

        PerfumeShopUserDetails userDetails = new PerfumeShopUserDetails(
                1L, "password", "username", "John", "Doe", authorities);

        boolean isAccountNonExpired = userDetails.isAccountNonExpired();

        assertTrue(isAccountNonExpired);
    }
    @Test
    public void testIsAccountNonLocked() {

        PerfumeShopUserDetails userDetails = new PerfumeShopUserDetails(
                1L, "password", "username", "John", "Doe", authorities);

        boolean isAccountNonLocked = userDetails.isAccountNonLocked();

        assertTrue(isAccountNonLocked);
    }
    @Test
    public void testIsEnabled() {

        PerfumeShopUserDetails userDetails = new PerfumeShopUserDetails(
                1L, "password", "username", "John", "Doe", authorities);

        boolean isEnabled = userDetails.isEnabled();

        assertTrue(isEnabled);
    }


}
