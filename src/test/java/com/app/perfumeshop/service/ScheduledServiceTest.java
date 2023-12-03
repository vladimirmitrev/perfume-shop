package com.app.perfumeshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ScheduledServiceTest {

    @InjectMocks
    private ScheduledService scheduledService;
    @Mock
    private OrderService orderService;
    @Test
    public void testDeleteAllCanceledOrders() {

        scheduledService.deleteAllCanceledOrders();

        Mockito.verify(orderService, times(1)).deleteAllCanceledOrders();
    }

}