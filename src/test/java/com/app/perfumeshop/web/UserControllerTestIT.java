package com.app.perfumeshop.web;

import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.util.TestDataUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTestIT {

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
}