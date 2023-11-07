package com.app.perfumeshop.model.mapper;

import com.app.perfumeshop.model.dto.ProductViewDTO;
import com.app.perfumeshop.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

//    @Mapping(source = "model.name", target = "model")
//    @Mapping(source = "model.brand.name", target = "brand")
//    ProductViewDTO productEntityToProductViewDTO(Product product);
}
