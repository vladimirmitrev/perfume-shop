package com.app.perfumeshop.web;

import com.app.perfumeshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/all")
    public String getAllProducts(Model model) {


    model.addAttribute("products", productService.getAllProducts());

        return "products";
    }


}
