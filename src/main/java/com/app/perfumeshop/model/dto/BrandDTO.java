package com.app.perfumeshop.model.dto;

import java.util.ArrayList;
import java.util.List;

public class BrandDTO {

    private String name;
    private List<ModelViewDTO> models;

    public String getName() {
        return name;
    }

    public BrandDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<ModelViewDTO> getModels() {
        return models;
    }

    public BrandDTO setModels(List<ModelViewDTO> models) {
        this.models = models;
        return this;
    }

    public BrandDTO addModel(ModelViewDTO model) {

        if (this.models == null) {
            this.models = new ArrayList<>();
        }
        this.models.add(model);

        return this;
    }
}
