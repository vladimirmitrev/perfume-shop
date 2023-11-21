package com.app.perfumeshop.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product = new Product();

    public OrderDetail() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public OrderDetail setProduct(Product product) {
        this.product = product;
        return this;
    }
}