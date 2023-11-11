package com.app.perfumeshop.model.entity;

import com.app.perfumeshop.model.enums.SizeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SizeEnum milliliters;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Positive
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Brand brand;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public SizeEnum getMilliliters() {
        return milliliters;
    }

    public Product setMilliliters(SizeEnum milliliters) {
        this.milliliters = milliliters;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Brand getBrand() {
        return brand;
    }

    public Product setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }
}
