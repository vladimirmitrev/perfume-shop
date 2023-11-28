package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.product.AddProductDTO;
import com.app.perfumeshop.model.dto.product.EditProductDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.*;
import com.app.perfumeshop.model.mapper.ProductMapper;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.repository.BrandRepository;
import com.app.perfumeshop.repository.CategoryRepository;
import com.app.perfumeshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CloudinaryService cloudinaryService,
                          ModelMapper modelMapper,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }


    private String getImage(MultipartFile file) throws IOException {
        String pictureUrl = "";
        if (file != null) {
            pictureUrl = this.cloudinaryService.uploadImage(file);
        }
        return pictureUrl;
    }
//    public Page<OfferDetailDTO> getAllOffers(Pageable pageable) {
////        return offerRepository.
////                findAll(pageable).
////                map(offerMapper::offerEntityToOfferDetailDto);
////
////    }

    public Page<ProductViewDTO> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable)
                .map(product -> {
                    ProductViewDTO productViewDTO = new ProductViewDTO();
                    productViewDTO.setName(product.getName());
                    productViewDTO.setBrand(product.getBrand().getName());
                    productViewDTO.setId(product.getId());
                    productViewDTO.setCategory(product.getCategory().getName());
                    productViewDTO.setMilliliters(product.getMilliliters());
                    productViewDTO.setImageUrl(product.getImageUrl());
                    productViewDTO.setDescription(product.getDescription());
                    productViewDTO.setPrice(product.getPrice());

                    return productViewDTO;
                });
    }
//        return productRepository.findAll().stream()
//                .map(productMapper::productEntityToProductViewDTO)
//                .toList();


    public void addProduct(AddProductDTO addProductDTO, PerfumeShopUserDetails userDetails) throws IOException {
        Brand brand = brandRepository
                .findByName(addProductDTO.getBrand());

        Category category = categoryRepository
                .findByName(addProductDTO.getCategory());

        MultipartFile photo = addProductDTO.getPhoto();
        String imageUrl = cloudinaryService.uploadImage(photo);

        Product product = new Product();
        product.setBrand(brand);
        product.setName(addProductDTO.getName())
                .setCategory(category)
                .setDescription(addProductDTO.getDescription())
                .setMilliliters(addProductDTO.getMilliliters())
                .setPrice(addProductDTO.getPrice())
                .setImageUrl(imageUrl);

        productRepository.save(product);
    }
    public void editProduct(EditProductDTO editProductDTO, PerfumeShopUserDetails userDetails, Long productId) throws IOException {
        Brand brand = brandRepository
                .findByName(editProductDTO.getBrand());

        Category category = categoryRepository
                .findByName(editProductDTO.getCategory());

        Product product = findById(productId);

        product.setBrand(brand);
        product.setName(editProductDTO.getName())
                .setCategory(category)
                .setDescription(editProductDTO.getDescription())
                .setMilliliters(editProductDTO.getMilliliters())
                .setPrice(editProductDTO.getPrice())
                .setImageUrl(editProductDTO.getImageUrl());

        productRepository.save(product);
    }

    private Product findById(Long productId) {
        return productRepository.findById(productId).get();
    }

    public Optional<ProductViewDTO> findProductById(Long id) {


        return productRepository.findById(id)
                .map(product -> {
                    ProductViewDTO productViewDTO = new ProductViewDTO();
                    productViewDTO
                            .setBrand(product.getBrand().getName())
                            .setName(product.getName())
                            .setCategory(product.getCategory().getName())
                            .setDescription(product.getDescription())
                            .setPrice(product.getPrice())
                            .setMilliliters(product.getMilliliters())
                            .setImageUrl(product.getImageUrl());

                    return productViewDTO;
                });

        //        return productRepository
//        .findById(id)
//        .map(productMapper::productEntityToProductViewDTO);
    }

    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);

    }

    public Product getProductById(Long productId) {

        return productRepository.findById(productId).get();
    }


    public List<ProductViewDTO> searchProducts(String keyword) {
        List<ProductViewDTO> products =
                productRepository.searchProductsByBrandOrNameOrDescription(keyword).stream().map(
                        (product -> {
                            ProductViewDTO productViewDTO = new ProductViewDTO();
                            productViewDTO.setName(product.getName());
                            productViewDTO.setBrand(product.getBrand().getName());
                            productViewDTO.setId(product.getId());
                            productViewDTO.setCategory(product.getCategory().getName());
                            productViewDTO.setMilliliters(product.getMilliliters());
                            productViewDTO.setImageUrl(product.getImageUrl());
                            productViewDTO.setDescription(product.getDescription());
                            productViewDTO.setPrice(product.getPrice());

                            return productViewDTO;
                        })).toList();
        return products;
    }

    public Page<ProductViewDTO> getAllProductByBrandId(Long id, Pageable pageable) {
        return productRepository.findProductsByBrand_Id(id, pageable)
                .map(productMapper::productEntityToProductViewDTO);
    }

//    public Page<ProductViewDTO> pageProducts(int pageNo) {
//        Pageable pageable = PageRequest.of(pageNo, 5);
//        Page<ProductViewDTO> productPages =
//                productRepository.pageProduct(pageable)
//                        .map(product -> {
//                            ProductViewDTO productViewDTO = new ProductViewDTO();
//                            productViewDTO.setName(product.getName());
//                            productViewDTO.setBrand(product.getBrand().getName());
//                            productViewDTO.setId(product.getId());
//                            productViewDTO.setCategory(product.getCategory().getName());
//                            productViewDTO.setMilliliters(product.getMilliliters());
//                            productViewDTO.setImageUrl(product.getImageUrl());
//                            productViewDTO.setDescription(product.getDescription());
//                            productViewDTO.setPrice(product.getPrice());
//
//                            return productViewDTO;
//                        });
//        return productPages;
//    }
}
