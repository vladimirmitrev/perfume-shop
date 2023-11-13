package com.app.perfumeshop.model.dto.order;

import java.math.BigDecimal;

public class OrderCheckoutDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String email;

    private String address;
    private String city;
    private String postCode;

    private String courier;

    private String notes;

    private BigDecimal totalPrice;

    public OrderCheckoutDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public OrderCheckoutDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public OrderCheckoutDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderCheckoutDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public OrderCheckoutDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderCheckoutDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public OrderCheckoutDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public OrderCheckoutDTO setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCourier() {
        return courier;
    }

    public OrderCheckoutDTO setCourier(String courier) {
        this.courier = courier;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public OrderCheckoutDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderCheckoutDTO setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
