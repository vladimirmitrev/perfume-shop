package com.app.perfumeshop.web;

import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.service.UserService;
import com.app.perfumeshop.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTestIT {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
//        userRepository.deleteAll();
    }
    @Test
    void testRegistrationSuccessful() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .param("email", "pesho@softuni.bg")
                        .param("username", "pesho")
                        .param("firstName", "Pesho")
                        .param("lastName", "Petrov")
                        .param("phoneNumber", "0888123456")
                        .param("password", "password23")
                        .param("confirmPassword", "password23")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }
    @Test
    void testRegistrationUnsuccessful() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("email", "pesho@softuni.bg")
                                .param("username", "pesho")
                                .param("firstName", "Pesho")
                                .param("lastName", "Petrov")
                                .param("phoneNumber", "0888123456")
                                .param("password", "password")
                                .param("confirmPassword", "password")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));
    }

    @Test
    @WithAnonymousUser
    void testRegisterGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }
    @Test
    @WithAnonymousUser
    void testLoginGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithAnonymousUser
    void testLoginSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login-error")
                        .param("email", "test@test.com")
                        .param("password", "password23")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testProfileView_LoggedIn() throws Exception {
        User principal = testDataUtils.createTestUser("test@example.com", "user");

        when(userService.findByEmail("test@example.com")).thenReturn(principal);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile")
                .principal(principal::getEmail))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testProfileView_NotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile")
                        .principal(() -> null))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl( "http://localhost/users/login"));
    }


}