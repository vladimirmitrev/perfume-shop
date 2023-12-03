package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.order.OrderCheckoutDTO;
import com.app.perfumeshop.model.entity.Order;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.service.OrderService;
import com.app.perfumeshop.service.UserService;
import com.app.perfumeshop.util.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @MockBean
    private OrderService orderService;
    @Autowired
    private TestDataUtils testDataUtils;

    @Test
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void testViewMyOrders() throws Exception {
        // Create a user and orders for the user (customize as needed)
        User user1 = testDataUtils.createTestUser("test1@test.com", "testUserMyOrders1");
        Order order1 = testDataUtils.createTestOrder(user1);
        Order order2 = testDataUtils.createTestOrder(user1);


        mockMvc.perform(MockMvcRequestBuilders.get("/my-orders"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("my-orders"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"))
                .andExpect(MockMvcResultMatchers.model().attribute("orders", List.of(order1, order2)));
    }
    @Test
    @WithMockUser(username = "test@test.com", roles = {"EMPLOYEE", "ADMIN"})
    public void testOrdersAllEndpoint() throws Exception {
        User user1 = testDataUtils.createTestUser("test2@test.com", "testUserMyOrders2");
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
//    @Test
//    @WithMockUser(username = "test@test.com", roles = "ADMIN")
//    public void testCancelOrderEndpoint() throws Exception {
//        // Mock the behavior of the orderService.cancelOrder() method
//        doNothing().when(orderService).cancelOrder(1L);
//
//        // Perform the POST request to the /cancel-order endpoint
//        mockMvc.perform(MockMvcRequestBuilders.post("/cancel-order")
//                        .param("id", "1")
//                        .param("action", "cancel"))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/my-orders"))
//                .andExpect(MockMvcResultMatchers.flash().attributeExists("success"))
//                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Order was canceled successfully!"))
//                .andDo(MockMvcResultHandlers.print());
//
//        // Optionally, you can verify interactions with mocked services
//        verify(orderService, times(1)).cancelOrder(1L);
//    }
//    @Test
//    @WithMockUser(username = "test@test.com", roles = "ADMIN")  // Adjust the user roles as needed
//    public void testCancelOrderEndpoint() throws Exception {
//        // Mock the behavior of the orderService.cancelOrder() method
//        doNothing().when(orderService).cancelOrder(1L);
//
//        // Perform the POST request to the corresponding controller method
//        mockMvc.perform(MockMvcRequestBuilders.post("/cancel-order")
//                        .param("id", "1")
//                        .param("action", "cancel"))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/my-orders"))
//                .andExpect(MockMvcResultMatchers.flash().attributeExists("success"))
//                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Order was canceled successfully!"))
//                .andDo(MockMvcResultHandlers.print());
//
//        // Optionally, you can verify interactions with mocked services
//        verify(orderService, times(1)).cancelOrder(1L);
//    }
//@Test
//@WithMockUser(username = "test@test.com", roles = {"EMPLOYEE", "ADMIN"})
//public void testCancelCustomerOrder() throws Exception {
//        User user = testDataUtils.createTestUser("abv@abv.bg", "testUser");
//        Order order = testDataUtils.createTestOrder(user);
//        order.setId(1L);
//        Long orderId = order.getId();
//
//    doNothing().when(orderService).cancelOrder(orderId);
//
//    mockMvc.perform(post("/cancel-customer-order")
//                    .param("id", String.valueOf(orderId))
//                    .param("action", "cancel"))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/orders-all"))
//            .andExpect(flash().attribute("success", "Order was canceled successfully!"));
//
//    // Verify that the cancelOrder method was called with the correct orderId
//    verify(orderService).cancelOrder(orderId);
//}
}