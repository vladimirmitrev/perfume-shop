package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.order.OrderCheckoutDTO;
import com.app.perfumeshop.model.entity.Order;
import com.app.perfumeshop.model.entity.OrderDetail;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.enums.OrderStatusEnum;
import com.app.perfumeshop.repository.OrderDetailRepository;
import com.app.perfumeshop.repository.OrderRepository;
import com.app.perfumeshop.repository.ShoppingCartRepository;
import com.app.perfumeshop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderService(ShoppingCartService shoppingCartService, OrderRepository orderRepository,
                        OrderDetailRepository detailRepository, UserRepository userRepository, UserService userService,
                        ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartService = shoppingCartService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = detailRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Transactional
    public Order placeOrder(ShoppingCart shoppingCart, OrderCheckoutDTO orderCheckoutDTO) {
        BigDecimal totalPrice = shoppingCart.getTotalPrice();
        BigDecimal discountPercentage = BigDecimal.valueOf(5);
        BigDecimal shipping = BigDecimal.valueOf(7.99);
        BigDecimal discountAmount = totalPrice.multiply(discountPercentage.divide(BigDecimal.valueOf(100)));
        BigDecimal discountedPrice = totalPrice.subtract(discountAmount).add(shipping);

        Order order = new Order();
        order
                .setCreatedOn(new Date())
                .setStatus(OrderStatusEnum.PROCESSING)
                .setCustomer(shoppingCart.getCustomer())
                .setTotalPrice(discountedPrice)
                .setShipped(false)
                .setPaymentMethod("CASH")
                .setShippingAddress(orderCheckoutDTO.getAddress())
                .setCity(orderCheckoutDTO.getCity())
                .setCourier(orderCheckoutDTO.getCourier().name())
                .setPostCode(orderCheckoutDTO.getPostCode());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        shoppingCart.getCartItems().forEach(cartItem -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        });

        order.setOrderDetailList(orderDetailList);

        shoppingCartService.clearCurrentCartById(shoppingCart.getId());
//        shoppingCartService.deleteCartById(shoppingCart.getId());

        return orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setShipped(false);
        order.setStatus(OrderStatusEnum.CANCELLED);
        orderRepository.save(order);
    }

    public void shippedOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setShipped(true)
                .setStatus(OrderStatusEnum.SHIPPED);
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Transactional
    public void deleteAllCanceledOrders() {
        List<Order> cancelledOrders = orderRepository.findAllByOrderByStatus(OrderStatusEnum.CANCELLED);
        cancelledOrders.stream().forEach(order -> {
            orderRepository.deleteById(order.getId());
        });
    }
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).get();
    }
}