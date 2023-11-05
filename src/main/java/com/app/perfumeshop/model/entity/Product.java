package com.app.perfumeshop.model.entity;

import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.MillilitersNameEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Model model;

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Model getModel() {
        return model;
    }

    public Product setModel(Model model) {
        this.model = model;
        return this;
    }


}
