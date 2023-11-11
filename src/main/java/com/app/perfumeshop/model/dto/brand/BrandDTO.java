package com.app.perfumeshop.model.dto.brand;

import com.app.perfumeshop.model.dto.product.ProductViewDTO;

import java.util.ArrayList;
import java.util.List;

public class BrandDTO {

    private String name;
    private List<ProductViewDTO> products;

    public String getName() {
        return name;
    }

    public BrandDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<ProductViewDTO> getProducts() {
        return products;
    }

    public BrandDTO setProducts(List<ProductViewDTO> products) {
        this.products = products;
        return this;
    }

    public BrandDTO addProduct(ProductViewDTO product) {

        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(product);

        return this;
    }
}
