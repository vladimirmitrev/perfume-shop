package com.app.perfumeshop.service;

import com.app.perfumeshop.model.entity.CartItem;
import com.app.perfumeshop.model.entity.Order;
import com.app.perfumeshop.model.entity.OrderDetail;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.enums.OrderStatusEnum;
import com.app.perfumeshop.repository.OrderDetailRepository;
import com.app.perfumeshop.repository.OrderRepository;
import com.app.perfumeshop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    public OrderService(ShoppingCartService shoppingCartService, OrderRepository orderRepository,
                        OrderDetailRepository detailRepository, UserRepository userRepository) {
        this.shoppingCartService = shoppingCartService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = detailRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order saveOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order
                .setCreatedOn(new Date())
                .setStatus(OrderStatusEnum.PROCESSING)
                .setCustomer(shoppingCart.getCustomer())
                .setTotalPrice(shoppingCart.getTotalPrice())
                .setAccepted(false)
                .setPaymentMethod("CASH");
        
        List<OrderDetail> orderDetailList = new ArrayList<>();

        shoppingCart.getCartItems().forEach(cartItem -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        });

        order.setOrderDetailList(orderDetailList);
        shoppingCartService.deleteCartById(shoppingCart.getId());

        return orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void acceptOrder(Long id) {

        Order order = orderRepository.findById(id).get();

        order.setAccepted(true)
                .setStatus(OrderStatusEnum.SHIPPED);

        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
}
