package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.repository.UserRoleRepository;
import com.app.perfumeshop.service.UserRoleService;
import com.app.perfumeshop.service.UserService;
import com.app.perfumeshop.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;
    @MockBean
    private UserRoleRepository userRoleRepository;
    @MockBean
    private UserRoleService userRoleService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = "ADMIN")
    public void testGetAllUsersWithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users-all"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ADMIN")
    public void testChangeUserRoleGetWithAdminRole() throws Exception {
        Long userId = 1L;
        UserViewDTO userViewDTO = createUserViewDTO(userId, "testUser@test.test", "testUser1");

        Mockito.when(userService.findUserById(userId)).thenReturn(userViewDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/change-role/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user-change-role"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ADMIN")
    public void testRemoveRole() throws Exception {
        Long userId = 1L;
        UserViewDTO userViewDTO = createUserEmployeeViewDTO(userId, "testUser@test.test", "testUser1");
        Mockito.when(userService.findUserById(userId)).thenReturn(userViewDTO);
        Mockito.doNothing().when(userRoleService).removeRole(userId);

        mockMvc.perform(patch("/users/roles/remove/{id}", userId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())  // Assuming it redirects
                .andExpect(redirectedUrl("/users/change-role/" + userId));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ADMIN")
    public void testAddRole() throws Exception {
        Long userId = 1L;
        UserViewDTO userViewDTO = createUserViewDTO(userId, "testUser@test.test", "testUser1");
        Mockito.when(userService.findUserById(userId)).thenReturn(userViewDTO);
        Mockito.doNothing().when(userRoleService).addRole(userId);

        mockMvc.perform(patch("/users/roles/add/{id}", userId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/change-role/" + userId))
                .andExpect(model().size(0));

    }

    private UserViewDTO createUserViewDTO(Long id, String email, String username) {
        initRoles();
        UserViewDTO userViewDTO = new UserViewDTO();
        userViewDTO
                .setId(id)
                .setEmail(email)
                .setUsername(username)
                .setFirstName("UserTest")
                .setLastName("UserovTest")
                .setPhoneNumber("0888123456")
                .setRoles(userRoleRepository
                        .findAll().stream().
                        filter(r -> r.getUserRole() == UserRoleEnum.USER).
                        toList());

        return userViewDTO;
    }
    private UserViewDTO createUserEmployeeViewDTO(Long id, String email, String username) {
        initRoles();
        UserViewDTO userViewDTO = new UserViewDTO();
        userViewDTO
                .setId(id)
                .setEmail(email)
                .setUsername(username)
                .setFirstName("UserTest")
                .setLastName("UserovTest")
                .setPhoneNumber("0888123456")
                .setRoles(userRoleRepository
                        .findAll().stream().
                        filter(r -> r.getUserRole() == UserRoleEnum.EMPLOYEE).
                        toList());

        return userViewDTO;
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole().setUserRole(UserRoleEnum.ADMIN);
            UserRole userRole = new UserRole().setUserRole(UserRoleEnum.USER);
            UserRole employeeRole = new UserRole().setUserRole(UserRoleEnum.EMPLOYEE);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
            userRoleRepository.save(employeeRole);
        }
    }


}
