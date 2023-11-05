package com.app.perfumeshop.model.entity;

import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.MillilitersNameEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "models")
public class Model extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MillilitersNameEnum milliliters;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryNameEnum category;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Brand brand;

    public String getName() {
        return name;
    }

    public Model setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Model setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public MillilitersNameEnum getMilliliters() {
        return milliliters;
    }

    public Model setMilliliters(MillilitersNameEnum milliliters) {
        this.milliliters = milliliters;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public Model setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Model setDescription(String description) {
        this.description = description;
        return this;
    }

    public Brand getBrand() {
        return brand;
    }

    public Model setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }


}
