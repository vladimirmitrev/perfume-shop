package com.app.perfumeshop.web;

import com.app.perfumeshop.exception.ObjectNotFoundException;
import com.app.perfumeshop.model.dto.product.AddOrUpdateProductDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.service.BrandsService;
import com.app.perfumeshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

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
    public String addProductGet(Model model) {
        if (!model.containsAttribute("addProductModel")) {
            model.addAttribute("addProductModel", new AddOrUpdateProductDTO());
        }
        model.addAttribute("brands", brandService.getAllBrands());

        return "product-add";
    }

    @PostMapping("/products/add")
    public String addProductPost(@Valid AddOrUpdateProductDTO addProductModel,
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

    @GetMapping("/products/{id}")
    public String getProductDetails(@PathVariable("id") Long id,
                                    Model model) {

        String productId = id.toString();

        ProductViewDTO product =
                productService.findProductById(id).orElseThrow(() ->
                        new ObjectNotFoundException("Offer with ID " + id + "not found"));

        model.addAttribute("product", product);

        return "product-details";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") Long id) {

        productService.deleteProductById(id);

        return "redirect:/products/all";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductGet(@PathVariable("id") Long id,
                       Model model) {

        ProductViewDTO editProductModel =
                productService.findProductById(id).orElseThrow(() ->
                        new ObjectNotFoundException("Offer with ID " + id + "not found"));

        model.addAttribute("editProductModel", editProductModel);

        return "product-edit";
    }

    @PostMapping("/products/edit/{id}")
    public String editProductPost(@PathVariable("id") Long id,
            @ModelAttribute("editProductModel2")
                                  @Valid AddOrUpdateProductDTO editProductModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal PerfumeShopUserDetails userDetails) {

        ProductViewDTO editProductModel2 =
                productService.findProductById(id).orElseThrow(() ->
                        new ObjectNotFoundException("Offer with ID " + id + "not found"));


//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("editProductModel", editProductModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductModel",
//                    bindingResult);
//
//
//            return "redirect:/products/edit/{id}";
//        }

        productService.addOrUpdateProduct(editProductModel, userDetails);

        return "redirect:/products/all";
    }
//    @PostMapping("/update-product/{id}")
//    public String updateProduct(@ModelAttribute("productDto") ProductEditDto productDto,
//                                Product product,
//                                RedirectAttributes redirectAttributes) {
//        try {
//
//            productServiceImpl.saveProduct(productDto, product);
//            redirectAttributes.addFlashAttribute("success", "Update successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
//        }
//        return "redirect:/products/0";
//    }



}
