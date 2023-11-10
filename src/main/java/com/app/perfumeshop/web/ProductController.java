package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.product.AddOrUpdateProductDTO;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.service.BrandsService;
import com.app.perfumeshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private final ProductService productService;
    private final BrandsService brandService;

    public ProductController(ProductService productService, BrandsService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }

    @GetMapping("/products/all")
    public String getAllProducts(Model model) {


    model.addAttribute("products", productService.getAllProducts());

        return "products";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {
        if (!model.containsAttribute("addProductModel")) {
            model.addAttribute("addProductModel", new AddOrUpdateProductDTO());
        }
        model.addAttribute("brands", brandService.getAllBrands());

        return "product-add";
    }
    @PostMapping("/products/add")
    public String addOffer(@Valid AddOrUpdateProductDTO addProductModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal PerfumeShopUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addProductModel", addProductModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductModel",
                    bindingResult);
            return "redirect:/products/add";
        }

        productService.addOrUpdateProduct(addProductModel, userDetails);

        return "redirect:/products/all";
    }


}
