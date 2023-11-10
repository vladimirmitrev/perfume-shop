package com.app.perfumeshop.model.dto;

import com.app.perfumeshop.model.entity.Category;
import com.app.perfumeshop.model.entity.Model;
import com.app.perfumeshop.model.enums.MillilitersNameEnum;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for {@link Model}
 */
public class ModelViewDTO implements Serializable {
    private  Long id;
    private  String name;
    private  String imageUrl;
    private  Category category;
    private  MillilitersNameEnum milliliters;
    private  String description;
    private  BigDecimal price;

    public Long getId() {
        return id;
    }

    public ModelViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ModelViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ModelViewDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ModelViewDTO setCategory(Category category) {
        this.category = category;
        return this;
    }

    public MillilitersNameEnum getMilliliters() {
        return milliliters;
    }

    public ModelViewDTO setMilliliters(MillilitersNameEnum milliliters) {
        this.milliliters = milliliters;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ModelViewDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ModelViewDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelViewDTO entity = (ModelViewDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.imageUrl, entity.imageUrl) &&
                Objects.equals(this.milliliters, entity.milliliters) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.price, entity.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, milliliters, description, price);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "imageUrl = " + imageUrl + ", " +
                "milliliters = " + milliliters + ", " +
                "description = " + description + ", " +
                "price = " + price + ")";
    }
}