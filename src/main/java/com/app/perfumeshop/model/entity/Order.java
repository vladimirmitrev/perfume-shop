package com.app.perfumeshop.model.entity;

import com.app.perfumeshop.model.enums.OrderStatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(nullable = false)
    private LocalDate createdOn;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
    @ManyToOne
    private User customer;

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Order setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public Order setStatus(OrderStatusEnum status) {
        this.status = status;
        return this;
    }

    public User getCustomer() {
        return customer;
    }

    public Order setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

}
