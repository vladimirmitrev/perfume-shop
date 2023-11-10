package com.app.perfumeshop.model.dto.model;

public class ModelDTO {

    private Long id;
    private String name;

    public ModelDTO() {
    }

    public Long getId() {
        return id;
    }

    public ModelDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ModelDTO setName(String name) {
        this.name = name;
        return this;
    }
}
