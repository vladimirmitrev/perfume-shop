package com.app.perfumeshop.web;

import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.repository.UserRoleRepository;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserRoleService;
import com.app.perfumeshop.service.UserService;
import com.app.perfumeshop.util.TestDataUtils;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestDataUtils testDataUtils;

    @MockBean
    private UserService userService;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setUp() {

    }
    @Test
    public void testHomeEndpointNotLogged() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/index")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeDoesNotExist("totalItems"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testHomeLoggedUser() throws Exception {
        User testUser = testDataUtils.createTestEmployee("test6655@test.com", "testUserHome");
        testUser.setId(1L);
        ShoppingCart shoppingCart = testDataUtils.createShoppingCart3(testUser, 2);
        shoppingCart.setTotalPrice(BigDecimal.TEN);
        when(userService.findByEmail("test@test.com")).thenReturn(testUser);
        when(shoppingCartService.findByUserId(testUser.getId())).thenReturn(shoppingCart);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/index")
                        .principal(() -> "test55@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andReturn();

        HttpSession mockSession = mvcResult.getRequest().getSession();

        assert mockSession != null;
        Assertions.assertEquals(2, mockSession.getAttribute("totalItems"));
    }

    @Test
    public void testAboutUsEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about-us"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("about-us"));
    }

    @Test
    public void testContactUsEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact-us"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("contact-us"));
    }
    @Test
    public void testMaintenanceEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/maintenance"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("maintenance"));
    }
}
