package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.product.AddOrUpdateProductDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.*;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.repository.BrandRepository;
import com.app.perfumeshop.repository.CategoryRepository;
import com.app.perfumeshop.repository.ModelRepository;
import com.app.perfumeshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ModelRepository modelRepository;

    public ProductService(ProductRepository productRepository, CloudinaryService cloudinaryService,
                          ModelMapper modelMapper,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository,
                          ModelRepository modelRepository) {
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.modelRepository = modelRepository;
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

    public void addOrUpdateProduct(AddOrUpdateProductDTO addOrUpdateProductDTO, PerfumeShopUserDetails userDetails) {
        Brand brand = brandRepository
                .findByName(addOrUpdateProductDTO.getBrand());

        Category category = categoryRepository
                .findByName(addOrUpdateProductDTO.getCategory());

        Model model = new Model();
        model.setBrand(brand);
        model.setName(addOrUpdateProductDTO.getModel())
                .setCategory(category)
                .setDescription(addOrUpdateProductDTO.getDescription())
                .setMilliliters(addOrUpdateProductDTO.getMilliliters())
                .setPrice(addOrUpdateProductDTO.getPrice())
                .setImageUrl(addOrUpdateProductDTO.getImageUrl());

        Product product = new Product();
        product.setModel(model);

        productRepository.save(product);
    }

    public Optional<ProductViewDTO> findProductById(Long id) {
        return modelRepository.findById(id)
                .map(model -> {
                    ProductViewDTO productViewDTO = new ProductViewDTO();
                    productViewDTO
                            .setBrand(model.getBrand().getName())
                            .setModel(model.getName())
                            .setCategory(model.getCategory().getName().toString())
                            .setDescription(model.getDescription())
                            .setPrice(model.getPrice())
                            .setMilliliters(model.getMilliliters())
                            .setImageUrl(model.getImageUrl());

                    return productViewDTO;
                });
    }

    public void deleteProductById(Long id) {
        modelRepository.deleteById(id);
        productRepository.deleteById(id);
    }
}
