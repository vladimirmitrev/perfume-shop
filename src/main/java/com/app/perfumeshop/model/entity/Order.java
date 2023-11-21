package com.app.perfumeshop.model.entity;

import com.app.perfumeshop.model.enums.OrderStatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(nullable = false)
    private Date createdOn;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private boolean isAccepted;
    @ManyToOne(fetch = FetchType.EAGER)
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetailList;

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }


    public Date getCreatedOn() {
        return createdOn;
    }

    public Order setCreatedOn(Date createdOn) {
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Order setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public Order setAccepted(boolean accepted) {
        isAccepted = accepted;
        return this;
    }
}
