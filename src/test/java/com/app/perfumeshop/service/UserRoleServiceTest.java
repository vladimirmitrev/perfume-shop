package com.app.perfumeshop.service;

import com.app.perfumeshop.exception.UserRoleNotFoundException;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRoleService userRoleService;

    private static final Long USER_ID = 1L;

    @BeforeEach
    public void setUp() {
        userRoleService = new UserRoleService(
                userRoleRepository,
                userRepository
        );
    }

    @Test
    public void testGetRole() {
        UserRoleEnum roleEnum = UserRoleEnum.EMPLOYEE;
        UserRole expectedRole = new UserRole();  // Create a UserRole object as needed for your test
        when(userRoleRepository.findByUserRole(roleEnum)).thenReturn(Optional.of(expectedRole));

        // Act
        UserRole resultRole = userRoleService.getRole(roleEnum);

        // Assert
        assertEquals(expectedRole, resultRole);
    }

    @Test
    public void testGetRoleNotFound() {
        // Arrange
        UserRoleEnum roleEnum = UserRoleEnum.USER;
        when(userRoleRepository.findByUserRole(roleEnum)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                UserRoleNotFoundException.class,
                () -> userRoleService.getRole(roleEnum)
        );
    }

    @Test
    public void testRemoveRole() {

        User user = new User();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        userRoleService.removeRole(USER_ID);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testAddRole() {
        User user = new User();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRoleRepository.findByUserRole(UserRoleEnum.EMPLOYEE)).thenReturn(Optional.of(new UserRole()));

        userRoleService.addRole(USER_ID);

        verify(userRepository, times(1)).save(user);
    }
}