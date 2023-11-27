package com.app.perfumeshop.model.dto.order;

import com.app.perfumeshop.model.enums.CourierNameEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class OrderCheckoutDTO {

    @NotEmpty(message = "Shipping address cannot be empty!")
    @Size(min = 5, max = 30, message = "Address length must be between 5 and 30 characters!")
    private String address;
    @NotEmpty(message = "City cannot be empty!")
    @Size(min = 3, max = 30, message = "Address length must be between 3 and 30 characters!")
    private String city;
    @NotEmpty(message = "Postcode cannot be empty!")
    @Size(min = 4, max = 10, message = "Postcode length must be between 4 and 10 characters!")
    private String postCode;
    @NotNull(message = "Choose a courier!")
    private CourierNameEnum courier;

    public OrderCheckoutDTO() {
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

    public CourierNameEnum getCourier() {
        return courier;
    }

    public OrderCheckoutDTO setCourier(CourierNameEnum courier) {
        this.courier = courier;
        return this;
    }
}
