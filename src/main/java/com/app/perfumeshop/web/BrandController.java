package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.service.BrandsService;
import com.app.perfumeshop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BrandController {

    private final ProductService productService;
    private final BrandsService brandsService;

    public BrandController(ProductService productService, BrandsService brandsService) {
        this.productService = productService;
        this.brandsService = brandsService;
    }

    @GetMapping("/brands")
    public String viewBrandsPage() {

        return "brands";
    }

    @GetMapping("/brand-products/{id}")
    public String getAllProductsFromThisBrand(@PathVariable("id") Long id, Model model,
                                              @PageableDefault(
                                                      sort = "name",
                                                      direction = Sort.Direction.ASC,
                                                      page = 0,
                                                      size = 10) Pageable pageable) {


        Page<ProductViewDTO> products = productService.getAllProductByBrandId(id, pageable);
        String brandName = brandsService.getBrandNameById(id);
        model.addAttribute("products", products);
        model.addAttribute("brand", brandName);
        return "brand-products";
    }
}
