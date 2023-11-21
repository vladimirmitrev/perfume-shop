package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.Order;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.service.OrderService;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(UserService userService, OrderService orderService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/order-checkout")
    public String checkOut(Principal principal, Model model) {

        UserViewDTO userViewDTO = userService.getUserByEmail(principal.getName());

        ShoppingCart shoppingCart = userService.findByEmail(principal.getName()).getShoppingCart();

        model.addAttribute("user", userViewDTO);
        model.addAttribute("title", "Check-Out");
        model.addAttribute("page", "Check-Out");
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("grandTotal", shoppingCart.getTotalItems());

        return "order-checkout";
    }


    @GetMapping("/order")
    public String order(Model model, Principal principal) {

        User user = userService.findByEmail(principal.getName());
        List<Order> orderList = user.getOrders();
        model.addAttribute("orders", orderList);

        return "order";
    }

//    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    @PostMapping("/add-order")
    public String addOrder(Model model,
                           Principal principal,
                           HttpSession session) {

        User user = userService.findByEmail(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        Order order = orderService.saveOrder(shoppingCart);

        session.removeAttribute("totalItems");
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Detail");
        model.addAttribute("page", "Order Detail");
        model.addAttribute("success", "Add order successfully");

        return "redirect:/order";
    }
}
