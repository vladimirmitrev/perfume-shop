package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.ProductViewDTO;
import com.app.perfumeshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CloudinaryService cloudinaryService,
                          ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }


    private String getImage(MultipartFile file) throws IOException {
        String pictureUrl = "";
        if (file != null) {
            pictureUrl = this.cloudinaryService.uploadImage(file);
        }
        return pictureUrl;
    }

    public List<ProductViewDTO> getAllProducts() {

        return productRepository.findAll().stream()
                .map(product -> {
                    ProductViewDTO productViewDTO = new ProductViewDTO();
                    productViewDTO.setModel(product.getModel().getName());
                    productViewDTO.setBrand(product.getModel().getBrand().getName());
                    productViewDTO.setId(product.getId());
                    productViewDTO.setCategory(product.getModel().getCategory().getName().toString());
                    productViewDTO.setMilliliters(product.getModel().getMilliliters());
                    productViewDTO.setImageUrl(product.getModel().getImageUrl());
                    productViewDTO.setDescription(product.getModel().getDescription());
                    productViewDTO.setPrice(product.getModel().getPrice());

                    return productViewDTO;
                }).collect(Collectors.toList());
    }
}
