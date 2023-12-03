package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.order.OrderCheckoutDTO;
import com.app.perfumeshop.model.entity.*;
import com.app.perfumeshop.model.enums.CourierNameEnum;
import com.app.perfumeshop.model.enums.OrderStatusEnum;
import com.app.perfumeshop.repository.*;
import com.app.perfumeshop.util.TestDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private OrderService orderService;

    @Autowired
    private TestDataUtils testDataUtils;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(shoppingCartService, orderRepository,
                orderDetailRepository,
                userRepository, userService,
                shoppingCartRepository);
    }
    @Test
    public void testPlaceOrder() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(100));
        shoppingCart.setCustomer(user);

        OrderCheckoutDTO orderCheckoutDTO = new OrderCheckoutDTO();
        orderCheckoutDTO.setAddress("Test Address");
        orderCheckoutDTO.setCity("Test City");
        orderCheckoutDTO.setCourier(CourierNameEnum.SPEEDY);
        orderCheckoutDTO.setPostCode("12345");


        doNothing().when(shoppingCartService).clearCurrentCartById(shoppingCart.getId());
        when(orderRepository.save(any())).thenReturn(new Order());

        Order result = orderService.placeOrder(shoppingCart, orderCheckoutDTO);

        verify(shoppingCartService).clearCurrentCartById(shoppingCart.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderDetailRepository, times(shoppingCart.getCartItems().size())).save(any(OrderDetail.class));
    }
    @Test
    void testCancelOrder() {
        User testUser = testDataUtils.createTestUser("test1@test.com", "testUserCancel");
        ShoppingCart shoppingCart = testDataUtils.createShoppingCart1(testUser, 1);
        shoppingCart.setId(1L);
        Long orderId = 1L;
        Order order = testDataUtils.createTestOrder(testUser);
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.cancelOrder(orderId);

        assertFalse(order.isShipped());
        assertEquals(OrderStatusEnum.CANCELLED, order.getStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(order);
    }
    @Test
    void testShippedOrder() {
        User testUser = testDataUtils.createTestUser("test2@test.com", "testUserShipped");
        ShoppingCart shoppingCart = testDataUtils.createShoppingCart1(testUser, 1);
        shoppingCart.setId(1L);
        Long orderId = 1L;
        Order order = testDataUtils.createTestOrder(testUser);
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.shippedOrder(orderId);

        assertTrue(order.isShipped());
        assertEquals(OrderStatusEnum.SHIPPED, order.getStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(order);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        assertSame(order1, result.get(0));
        assertSame(order2, result.get(1));
        verify(orderRepository).findAll();
    }
    @Test
    void testGetAllOrdersEmptyList() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<Order> result = orderService.getAllOrders();

        assertTrue(result.isEmpty());
        verify(orderRepository).findAll();
    }
    @Test
    void testDeleteAllCanceledOrders() {
        Order cancelledOrder1 = new Order();
        Order cancelledOrder2 = new Order();
        List<Order> cancelledOrders = Arrays.asList(cancelledOrder1, cancelledOrder2);

        when(orderRepository.findAllByOrderByStatus(OrderStatusEnum.CANCELLED)).thenReturn(cancelledOrders);
        
        orderService.deleteAllCanceledOrders();
        
        verify(orderRepository, times(1)).findAllByOrderByStatus(OrderStatusEnum.CANCELLED);
        verify(orderRepository, times(2)).deleteById(cancelledOrder1.getId());
        verify(orderRepository, times(2)).deleteById(cancelledOrder2.getId());
    }
    @Test
    void testFindOrderById() {
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        Order result = orderService.findOrderById(1L);

        assertSame(expectedOrder, result);
        verify(orderRepository).findById(1L);
    }

    @Test
    void testFindOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.findOrderById(1L));
        verify(orderRepository).findById(1L);
    }
    @Test
    void testCreateOrderDetails() {
        Product product = new Product();
        Order order = new Order();
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product);
        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(product);

        List<CartItem> cartItems = List.of(cartItem1, cartItem2);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setOrder(order);
        orderDetail1.setProduct(cartItem1.getProduct());
        orderDetailRepository.save(orderDetail1);
        List<OrderDetail> mockOrderDetailList = mock(List.class);
        mockOrderDetailList.add(orderDetail1);

        order.setOrderDetailList(mockOrderDetailList);
        orderRepository.save(order);

        verify(orderDetailRepository, times(1)).save(any(OrderDetail.class));
        verify(mockOrderDetailList, times(1)).add(any(OrderDetail.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
