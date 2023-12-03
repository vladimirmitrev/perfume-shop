package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.mapper.UserMapper;
import com.app.perfumeshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;


    @Test
    public void testFindByEmail() {

        String testEmail = "test@example.com";
        User mockUser = new User(); // Create a mock User object for testing
        Mockito.when(userRepository.findUserByEmail(testEmail)).thenReturn(java.util.Optional.of(mockUser));
        User result = userService.findByEmail(testEmail);

        assertNotNull(result);
        assertSame(mockUser, result);
    }
    @Test
    public void testGetAllUsers() {

        List<User> mockUsers = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userMapper.userEntityToUserDto(Mockito.any(User.class))).thenReturn(new UserViewDTO());


        List<UserViewDTO> result = userService.getAllUser();

        assertNotNull(result);
        assertEquals(mockUsers.size(), result.size());
    }
    @Test
    public void testFindUserById() {
        Long testId = 1L;
        User mockUser = new User();
        Mockito.when(userRepository.findById(testId)).thenReturn(java.util.Optional.of(mockUser));
        Mockito.when(userMapper.userEntityToUserDto(Mockito.any(User.class))).thenReturn(new UserViewDTO());

        UserViewDTO result = userService.findUserById(testId);

        assertNotNull(result);
    }
    @Test
    public void testGetUserByEmail() {
        String testEmail = "test@example.com";
        User mockUser = new User();
        Mockito.when(userRepository.findUserByEmail(testEmail)).thenReturn(java.util.Optional.of(mockUser));
        Mockito.when(userMapper.userEntityToUserDto(Mockito.any(User.class))).thenReturn(new UserViewDTO());

        UserViewDTO result = userService.getUserByEmail(testEmail);

        assertNotNull(result);
    }
}
