package com.app.perfumeshop.model.dto.product;

import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.SizeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class EditProductDTO {


    @NotEmpty
    @Size(min = 3, max = 30, message = "Brand name must be between 5 and 30 characters")
    private String brand;
    @NotEmpty
    @Size(min = 3, max = 30, message = "Model name must be between 5 and 30 characters")
    private String name;
    @NotEmpty
    @NotNull(message = "Please choose a file")
    private String imageUrl;
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

    public EditProductDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public EditProductDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public EditProductDTO setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public SizeEnum getMilliliters() {
        return milliliters;
    }

    public EditProductDTO setMilliliters(SizeEnum milliliters) {
        this.milliliters = milliliters;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EditProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
