package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.UserRegisterDTO;
import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.model.mapper.UserMapper;
import com.app.perfumeshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    @Mock
    private UserRoleService userRoleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUser() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("testPassword");

        UserRole userRole = new UserRole();
        Mockito.when(userRoleService.getRole(UserRoleEnum.USER)).thenReturn(userRole);

        User newUser = new User();
        Mockito.when(userMapper.userDtoToUserEntity(userRegisterDTO)).thenReturn(newUser);

        Mockito.when(passwordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("hashedPassword");

        userService.registerUser(userRegisterDTO);

        Mockito.verify(userRoleService, Mockito.times(1)).getRole(UserRoleEnum.USER);
        Mockito.verify(userMapper, Mockito.times(1)).userDtoToUserEntity(userRegisterDTO);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(userRegisterDTO.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(Mockito.any(User.class));
    }
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
