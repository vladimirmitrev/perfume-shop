package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.BrandDTO;
import com.app.perfumeshop.model.dto.BrandViewDTO;
import com.app.perfumeshop.model.dto.ModelViewDTO;
import com.app.perfumeshop.model.dto.model.ModelDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.Brand;
import com.app.perfumeshop.model.entity.Model;
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

//    public List<BrandDTO> getAllBrands() {
//
//        List<BrandDTO> allBrands = brandRepository.findAll()
//                .stream()
//                .map(this::mapBrand)
//                .toList();
//
//        return allBrands;
//    }
//
//    private BrandDTO mapBrand(Brand brand) {
//
//        List<ModelViewDTO> models = brand.getModels()
//                .stream()
//                .map(this::mapModel)
//                .toList();
//
//        return new BrandDTO()
//                .setModels(models)
//                .setName(brand.getName());
//    }
//
//    private ModelViewDTO mapModel(Model model) {
//            return new ModelViewDTO()
//                    .setId(model.getId())
//                    .setName(model.getName());
//
//    }
}
