package com.app.perfumeshop.web;

import com.app.perfumeshop.model.entity.*;
import com.app.perfumeshop.service.ProductService;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import com.app.perfumeshop.util.TestDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTestIT {

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @BeforeEach
    void setUp() {

    }
    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testShoppingCart() throws Exception {
        User testUser = testDataUtils.createTestUser("test55@test.com", "testUserShopCart");
        testUser.setId(1L);
        ShoppingCart shoppingCart = testDataUtils.createShoppingCart3(testUser, 1);
        shoppingCart.setTotalPrice(BigDecimal.TEN);
        when(userService.findByEmail("test@test.com")).thenReturn(testUser);
        when(shoppingCartService.findByUserId(testUser.getId())).thenReturn(shoppingCart);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("cart"))
                .andExpect(MockMvcResultMatchers.model().attribute("shoppingCart", shoppingCart));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testAddToCart() throws Exception {
        Category category = testDataUtils.createTestCategory();
        Brand brand = testDataUtils.createTestBrand2();
        User testUser = testDataUtils.createTestUser("test324324@test.com", "testUser444");
        Product product = testDataUtils.createTestProduct(brand, category, testUser);
        product.setId(1L);
        testUser.setId(1L);
        when(productService.getProductById(1L)).thenReturn(product);
        when(userService.findByEmail("test@test.com")).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add/1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/all"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testUpdateCart() throws Exception {
        Category category = testDataUtils.createTestCategory();
        Brand brand = testDataUtils.createTestBrand1();
        User testUser = testDataUtils.createTestUser("test@test.com", "testUser");
        Product product = testDataUtils.createTestProduct(brand, category, testUser);
        product.setId(1L);
        testUser.setId(1L);
        ShoppingCart shoppingCart = testDataUtils.createShoppingCart1(testUser, 1);
        shoppingCart.setTotalPrice(BigDecimal.TEN);

        when(userService.findByEmail("test@test.com")).thenReturn(testUser);
        when(productService.getProductById(1L)).thenReturn(product);
        when(shoppingCartService.updateItemInCart(product, 2, testUser)).thenReturn(shoppingCart);

        mockMvc.perform(MockMvcRequestBuilders.post("/update-cart")
                        .param("id", "1")
                        .param("quantity", "2")
                        .param("action", "update")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cart"));
    }
    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testRemoveFromCart() throws Exception {
        // Arrange
        Category category = testDataUtils.createTestCategory();
        Brand brand = testDataUtils.createTestBrand3();
        User testUser = testDataUtils.createTestUser("test24235@test.com", "testUser");
        Product product = testDataUtils.createTestProduct(brand, category, testUser);
        product.setId(1L);
        testUser.setId(1L);
        ShoppingCart shoppingCart = testDataUtils.createShoppingCart2(testUser,2);
        shoppingCart.setTotalPrice(BigDecimal.TEN);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(BigDecimal.TEN);
        shoppingCart.getCartItems().add(cartItem);

        when(userService.findByEmail("test@test.com")).thenReturn(testUser);
        when(productService.getProductById(1L)).thenReturn(product);
        when(shoppingCartService.removeItemFromCart(product, testUser)).thenReturn(shoppingCart);

        mockMvc.perform(MockMvcRequestBuilders.post("/update-cart")
                        .param("id", "1")
                        .param("action", "delete")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cart"));
    }

}
