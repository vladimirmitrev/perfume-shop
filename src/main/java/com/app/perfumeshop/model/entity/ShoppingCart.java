package com.app.perfumeshop.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {


    @OneToOne(fetch = FetchType.EAGER)
    private User customer;

    @OneToMany
    private List<CartItem> cartItems;

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
}
