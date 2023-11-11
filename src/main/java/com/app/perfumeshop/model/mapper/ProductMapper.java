package com.app.perfumeshop.model.mapper;

import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "brand.name", target = "brand")
    ProductViewDTO productEntityToProductViewDTO(Product product);
}
