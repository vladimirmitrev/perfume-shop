package com.app.perfumeshop.service;

import com.app.perfumeshop.model.entity.OrderDetail;
import com.app.perfumeshop.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetail> findOrderById(Long id) {
        return orderDetailRepository.findByOrder_Id(id);
    }
}
