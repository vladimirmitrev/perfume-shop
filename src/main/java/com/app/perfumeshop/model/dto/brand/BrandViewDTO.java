package com.app.perfumeshop.model.dto.brand;

public class BrandViewDTO {

    private Long id;
    private String name;

    public BrandViewDTO() {
    }

    public Long getId() {
        return id;
    }

    public BrandViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BrandViewDTO setName(String name) {
        this.name = name;
        return this;
    }
}
