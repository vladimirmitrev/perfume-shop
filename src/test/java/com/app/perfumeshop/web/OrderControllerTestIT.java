package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.order.OrderCheckoutDTO;
import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.Order;
import com.app.perfumeshop.model.entity.OrderDetail;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.mapper.UserMapper;
import com.app.perfumeshop.service.OrderDetailService;
import com.app.perfumeshop.service.OrderService;
import com.app.perfumeshop.service.UserService;
import com.app.perfumeshop.util.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderDetailService orderDetailService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TestDataUtils testDataUtils;

    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testViewMyOrders() throws Exception {
        User testUser2 = testDataUtils.createTestUser("test7567657@test.com", "testUserMyOrders1666");
        Order order1 = testDataUtils.createTestOrder(testUser2);
        Order order2 = testDataUtils.createTestOrder(testUser2);

        when(userService.findByEmail("test@test.com")).thenReturn(testUser2);
//        when(userService.getOrders(testUser2)).thenReturn(List.of(order1, order2));

        mockMvc.perform(MockMvcRequestBuilders.get("/my-orders"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("my-orders"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"EMPLOYEE", "ADMIN"})
    public void testOrdersAllEndpoint() throws Exception {
        User user1 = testDataUtils.createTestUser("test876342@test.com", "testUserMyOrders2");
        Order order1 = testDataUtils.createTestOrder(user1);
        Order order2 = testDataUtils.createTestOrder(user1);

        when(orderService.getAllOrders()).thenReturn(List.of(order1, order2));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders-all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("orders-all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"))
                .andExpect(MockMvcResultMatchers.model().attribute("orders", List.of(order1, order2)));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = "ADMIN")
    public void testCancelOrderEndpoint() throws Exception {
        doNothing().when(orderService).cancelOrder(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/cancel-order")
                        .param("id", "1")
                        .param("action", "cancel")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/orders-all"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("success"))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Order was canceled successfully!"))
                .andDo(MockMvcResultHandlers.print());

        verify(orderService, times(1)).cancelOrder(1L);
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"EMPLOYEE", "ADMIN"})
    public void testCancelCustomerOrder() throws Exception {
        User user = testDataUtils.createTestUser("abv345@abv.bg", "testUser67899");
        Order order = testDataUtils.createTestOrder(user);
        order.setId(1L);
        Long orderId = order.getId();

        doNothing().when(orderService).cancelOrder(orderId);

        mockMvc.perform(post("/cancel-customer-order")
                        .param("id", String.valueOf(orderId))
                        .param("action", "cancel")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders-all"))
                .andExpect(flash().attribute("success", "Order was canceled successfully!"));

        verify(orderService).cancelOrder(orderId);
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"EMPLOYEE", "ADMIN"})
    public void testShipOrder() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.post("/ship-order")
                        .param("id", String.valueOf(orderId))
                        .param("action", "ship")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/orders-all"))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Order was shipped successfully!"));

        verify(orderService, times(1)).shippedOrder(orderId);
    }
    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testOrderDetails() throws Exception {
        Long orderId = 1L;
        User testUser234234 = testDataUtils.createTestUser("test3428884@test.com", "testUser223334234");
        Order mockOrder = testDataUtils.createTestOrder(testUser234234);
        List<OrderDetail> mockOrderDetails = new ArrayList<>();
        when(orderService.findOrderById(orderId)).thenReturn(mockOrder);
        when(orderDetailService.findOrderById(orderId)).thenReturn(mockOrderDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/order-details/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("order-details"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderDetail", mockOrderDetails))
                .andExpect(MockMvcResultMatchers.model().attribute("order", mockOrder));

        verify(orderService, times(1)).findOrderById(orderId);
        verify(orderDetailService, times(1)).findOrderById(orderId);
    }
//    @Test
//    @WithMockUser(username = "test@test.com", roles = "USER")
//    public void testCheckOut() throws Exception {
//        // Mock data for user and shopping cart
//         User mockUser = testDataUtils.createTestUser("test@test.com", "testUser2312");
//        ShoppingCart mockShoppingCart = testDataUtils.createShoppingCart1(mockUser, 3);
//
////        when(userService.getUserByEmail("test@test.com")).thenReturn(mockUser.map(userMapper::userEntityToUserDto));
//        when(userService.findByEmail("test@test.com")).thenReturn(mockUser);
////        when(mockUser.getShoppingCart()).thenReturn(mockShoppingCart);
////        when(mockShoppingCart.getTotalItems()).thenReturn(3); // Set the total items as needed
//
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/order-checkout"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("order-checkout"))
//                .andExpect(MockMvcResultMatchers.model().attribute("shippingInfo", new OrderCheckoutDTO()))
//                .andExpect(MockMvcResultMatchers.model().attribute("user", mockUser))
//                .andExpect(MockMvcResultMatchers.model().attribute("title", "Check-Out"))
//                .andExpect(MockMvcResultMatchers.model().attribute("page", "Check-Out"))
//                .andExpect(MockMvcResultMatchers.model().attribute("shoppingCart", mockShoppingCart))
//                .andExpect(MockMvcResultMatchers.model().attribute("grandTotal", BigDecimal.TEN));
//
//        // Verify that the getUserByEmail method of the userService was called with the correct email
//        verify(userService, times(1)).getUserByEmail("test@test.com");
//
//        // Verify that the findByEmail method of the userService was called with the correct email
//        verify(userService, times(1)).findByEmail("test@test.com");
//    }
}