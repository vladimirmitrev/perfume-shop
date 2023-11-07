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
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Category category;
    private final MillilitersNameEnum milliliters;
    private final String description;
    private final BigDecimal price;

    public ModelViewDTO(Long id, String name, String imageUrl, Category category, MillilitersNameEnum milliliters, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.milliliters = milliliters;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MillilitersNameEnum getMilliliters() {
        return milliliters;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
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