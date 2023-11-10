package com.app.perfumeshop.model.dto.product;

import com.app.perfumeshop.model.enums.MillilitersNameEnum;

import java.math.BigDecimal;

public class ProductViewDTO {

    private Long id;
    private String brand;
    private String model;
    private  String imageUrl;
    private  String category;
    private  MillilitersNameEnum milliliters;
    private  String description;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public ProductViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductViewDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductViewDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductViewDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductViewDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public MillilitersNameEnum getMilliliters() {
        return milliliters;
    }

    public ProductViewDTO setMilliliters(MillilitersNameEnum milliliters) {
        this.milliliters = milliliters;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductViewDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductViewDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
