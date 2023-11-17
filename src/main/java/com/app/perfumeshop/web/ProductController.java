package com.app.perfumeshop.web;

import com.app.perfumeshop.exception.ObjectNotFoundException;
import com.app.perfumeshop.model.dto.product.AddOrUpdateProductDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.user.PerfumeShopUserDetails;
import com.app.perfumeshop.service.BrandsService;
import com.app.perfumeshop.service.ProductService;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProductController {

    private final ProductService productService;
    private final BrandsService brandService;
    private final UserService userService;

    private final ShoppingCartService shoppingCartService;

    public ProductController(ProductService productService, BrandsService brandService, UserService userService, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.brandService = brandService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/products/all")
    public String getAllProducts(Model model, HttpSession session, Principal principal) {

        if (principal != null) {
            session.setAttribute("username", principal.getName());
            User user = userService.findByEmail(principal.getName());
            ShoppingCart shoppingCart = shoppingCartService.findByUserId(user.getId());

            session.setAttribute("totalItems", shoppingCart != null ? shoppingCart.getTotalItems() : 0);
        } else {
            session.removeAttribute("username");
        }

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

    @GetMapping("/products/search")
    public String searchProducts() {


        return "/products-search";
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
