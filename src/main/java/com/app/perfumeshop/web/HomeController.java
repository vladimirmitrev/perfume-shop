package com.app.perfumeshop.web;

import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public HomeController(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {

        if (principal != null) {
            session.setAttribute("username", principal.getName());
            User user = userService.findByEmail(principal.getName());
            ShoppingCart shoppingCart = shoppingCartService.findByUserId(user.getId());

            model.addAttribute("userModel", user);
            session.setAttribute("totalItems", shoppingCart != null ? shoppingCart.getTotalItems() : 0);
        } else {
            session.removeAttribute("username");
        }
        return "index";
    }

    @GetMapping("/contact-us")
    public String contact() {


        return "contact-us";
    }

    @GetMapping("/about-us")
    public String about() {


        return "about-us";
    }

}
