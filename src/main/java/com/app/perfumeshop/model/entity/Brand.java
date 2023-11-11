package com.app.perfumeshop.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public Brand() {
    }

    public String getName() {
        return name;
    }

    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Brand setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

}
