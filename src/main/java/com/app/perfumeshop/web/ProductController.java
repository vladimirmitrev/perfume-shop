package com.app.perfumeshop.web;

import com.app.perfumeshop.exception.ObjectNotFoundException;
import com.app.perfumeshop.model.dto.product.AddProductDTO;
import com.app.perfumeshop.model.dto.product.EditProductDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.repository.ProductRepository;
import com.app.perfumeshop.service.BrandsService;
import com.app.perfumeshop.service.ProductService;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final BrandsService brandService;
    private final UserService userService;

    private final ShoppingCartService shoppingCartService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, BrandsService brandService, UserService userService, ShoppingCartService shoppingCartService,
                             ProductRepository productRepository) {
        this.productService = productService;
        this.brandService = brandService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.productRepository = productRepository;
    }

    @GetMapping("/products/all")
    public String getAllProducts(Model model,
                                 HttpSession session,
                                 Principal principal,
                                 @PageableDefault(
                                         sort = "brand",
                                         direction = Sort.Direction.ASC,
                                         page = 0,
                                         size = 10) Pageable pageable) {

        if (principal != null) {
            session.setAttribute("username", principal.getName());
            User user = userService.findByEmail(principal.getName());
            ShoppingCart shoppingCart = shoppingCartService.findByUserId(user.getId());

            session.setAttribute("totalItems", shoppingCart != null ? shoppingCart.getTotalItems() : 0);
        } else {
            session.removeAttribute("username");
        }

        Page<ProductViewDTO> products = productService.getAllProducts(pageable);
        model.addAttribute("products", products);

        if (products.isEmpty()) {
            model.addAttribute("emptyShop", "Shop is empty");
        }

        return "products";
    }

//    @GetMapping("/products/{pageNo}")
//    public String productsPage(@PathVariable("pageNo") int pageNo,
//                               Model model) {
//
//        Page<ProductViewDTO> products = productService.pageProducts(pageNo);
//
//        model.addAttribute("title", "Manage Product");
//        model.addAttribute("size", products.getSize());
//        model.addAttribute("totalPages", products.getTotalPages());
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("products", products);
//
//        return "products";
//    }

    @GetMapping("/products/add")
    public String addProductGet(Model model) {
        if (!model.containsAttribute("addProductModel")) {
            model.addAttribute("addProductModel", new AddProductDTO());
        }
        model.addAttribute("brands", brandService.getAllBrands());

        return "product-add";
    }

    @PostMapping("/products/add")
    public String addProductPost(@Valid AddProductDTO addProductModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal PerfumeShopUserDetails userDetails) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addProductModel", addProductModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductModel",
                    bindingResult);
            return "redirect:/products/add";
        }

        productService.addProduct(addProductModel);

        return "redirect:/products/all";
    }

    @GetMapping("/products/details/{id}")
    public String getProductDetails(@PathVariable("id") Long id,
                                    Model model) {

        String productId = id.toString();

        ProductViewDTO product =
                productService.findProductById(id).orElseThrow(() ->
                        new ObjectNotFoundException("Product with ID " + id + "not found"));

        model.addAttribute("product", product);

        return "product-details";
    }

    @PostMapping("/products/delete/{id}")
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
                        new ObjectNotFoundException("Product with ID " + id + "not found"));

        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("editProductModel", editProductModel);

        return "product-edit";
    }

    @PatchMapping("/products/edit/{id}")
    public String editProductPost(@PathVariable("id") Long id,
                                  @ModelAttribute("editProductModel2")
                                  @Valid EditProductDTO editProductModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal PerfumeShopUserDetails userDetails) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editProductModel", editProductModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editProductModel",
                    bindingResult);

            return "redirect:/products/edit/{id}";
        }

        ProductViewDTO editProductModel2 =
                productService.findProductById(id).orElseThrow(() ->
                        new ObjectNotFoundException("Offer with ID " + id + "not found"));


        productService.editProduct(editProductModel, id);

        return "redirect:/products/all";
    }

    @GetMapping("/search")
    public String searchGet(Model model) {

        model.addAttribute("noProductsFound", "No products found");

        return "search";
    }

    @PostMapping("/search")
    public String searchPost(@RequestParam(name = "query", required = false) String query, Model model,
                             @PageableDefault(
                                     sort = "name",
                                     direction = Sort.Direction.ASC,
                                     page = 0,
                                     size = 100) Pageable pageable) {

        Page<ProductViewDTO> searchResults = productService.searchProducts(query, pageable);

        if (searchResults == null || searchResults.isEmpty()) {
            model.addAttribute("noProductsFound", "No products found");
        }
        model.addAttribute("searchResults", searchResults);

        return "search";
    }
}
