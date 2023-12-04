package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.brand.BrandViewDTO;
import com.app.perfumeshop.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandsService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public BrandsService(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }


    public List<BrandViewDTO> getAllBrands() {

        return brandRepository.findAll()
                .stream().map((brand) ->
                        modelMapper.map(brand, BrandViewDTO.class))
                .toList();
    }

    public String getBrandNameById(Long id) {
        return brandRepository.findById(id).get().getName();
    }
}
