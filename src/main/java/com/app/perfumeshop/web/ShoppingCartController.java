package com.app.perfumeshop.web;

import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.service.ProductService;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String shoppingCart(Model model, Principal principal, HttpSession httpSession) {

        User user = userService.findByEmail(principal.getName());
        ShoppingCart shoppingCart = shoppingCartService.findByUserId(user.getId());

        if (shoppingCart == null) {
            model.addAttribute("emptyCart", "Your shopping cart is empty");

        }

//        if (shoppingCart != null) {
//            model.addAttribute("subTotal", shoppingCart.getTotalPrice());
//        }

        httpSession.setAttribute("totalItems", shoppingCart != null ? shoppingCart.getTotalItems() : 0);
        model.addAttribute("shoppingCart", shoppingCart);


        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable("id") Long productId,
                            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                            Principal principal) {

        Product product = productService.getProductById(productId);

        User user = userService.findByEmail(principal.getName());

        this.shoppingCartService.addProductToCart(product, user ,quantity);

        return "redirect:/products/all";
    }
}
