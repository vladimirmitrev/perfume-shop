package com.app.perfumeshop.model.dto.product;

import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.SizeEnum;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddProductDTO {


    @NotEmpty
    @Size(min = 3, max = 30, message = "Brand name must be between 5 and 30 characters")
    private String brand;
    @NotEmpty
    @Size(min = 3, max = 30, message = "Model name must be between 5 and 30 characters")
    private String name;
//    @NotEmpty
//    @NotNull(message = "Please choose a file")
    private MultipartFile photo;
    @NotNull(message = "Please choose category")
    private CategoryNameEnum category;
    @NotNull(message = "Please a size category")
    private SizeEnum milliliters;
    @NotEmpty
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;
    @Positive
    @NotNull(message = "Please set a price")
    private BigDecimal price;


    public String getBrand() {
        return brand;
    }

    public AddProductDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getName() {
        return name;
    }

    public AddProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public AddProductDTO setPhoto(MultipartFile photo) {
        this.photo = photo;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public AddProductDTO setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public SizeEnum getMilliliters() {
        return milliliters;
    }

    public AddProductDTO setMilliliters(SizeEnum milliliters) {
        this.milliliters = milliliters;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
