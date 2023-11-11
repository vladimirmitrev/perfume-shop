package com.app.perfumeshop.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {


    @OneToOne(fetch = FetchType.EAGER)
    private User customer;

    private BigDecimal totalPrice;

    private int totalItems;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.totalItems = 0;
        this.totalPrice = BigDecimal.valueOf(0.00);
    }

    public User getCustomer() {
        return customer;
    }

    public ShoppingCart setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public ShoppingCart setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        return this;
    }


    public Integer getTotalItems() {
        return totalItems;
    }

    public ShoppingCart setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCart setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public ShoppingCart setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        return this;
    }
}
