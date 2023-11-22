package com.app.perfumeshop.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    private final OrderService orderService;

    public ScheduledService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "* 0 03 * * *")
    public void deleteAllCanceledOrders() {
        orderService.deleteAllCanceledOrders();
    }
}
