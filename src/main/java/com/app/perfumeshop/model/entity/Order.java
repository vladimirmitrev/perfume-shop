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

    @Column(nullable = true)
    private String shippingAddress;

    @Column(nullable = true)
    private String city;
    @Column(nullable = true)
    private String postCode;

    @Column(nullable = true)
    private String courier;
    @Column(nullable = false)
    private boolean isShipped;
    @ManyToOne(fetch = FetchType.EAGER)
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetailList;

    public Order() {
    }

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

    public boolean isShipped() {
        return isShipped;
    }

    public Order setShipped(boolean shipped) {
        isShipped = shipped;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Order setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Order setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCourier() {
        return courier;
    }

    public Order setCourier(String courier) {
        this.courier = courier;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public Order setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }
}
