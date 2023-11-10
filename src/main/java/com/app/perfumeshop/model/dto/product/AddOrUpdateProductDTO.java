package com.app.perfumeshop.model.dto.product;

import com.app.perfumeshop.model.entity.Category;
import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.MillilitersNameEnum;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AddOrUpdateProductDTO {


    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotEmpty
    private String imageUrl;
    @NotNull
    private CategoryNameEnum category;
    @NotNull
    private MillilitersNameEnum milliliters;
    @NotEmpty
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;
    @Positive
    @NotNull
    private BigDecimal price;


    public String getBrand() {
        return brand;
    }

    public AddOrUpdateProductDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public AddOrUpdateProductDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AddOrUpdateProductDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public AddOrUpdateProductDTO setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public MillilitersNameEnum getMilliliters() {
        return milliliters;
    }

    public AddOrUpdateProductDTO setMilliliters(MillilitersNameEnum milliliters) {
        this.milliliters = milliliters;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddOrUpdateProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddOrUpdateProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
