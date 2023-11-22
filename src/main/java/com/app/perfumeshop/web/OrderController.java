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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @GetMapping("/my-orders")
    public String order(Model model, Principal principal) {

        User user = userService.findByEmail(principal.getName());
        List<Order> orderList = user.getOrders();
        model.addAttribute("orders", orderList);

        return "my-orders";
    }
    @GetMapping("/orders-all")
    public String ordersAll(Model model) {

        List<Order> orderList = orderService.getAllOrders();

        model.addAttribute("orders", orderList);

        return "orders-all";
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

        return "redirect:/my-orders";
    }
    @RequestMapping(value = "/cancel-order", method = {RequestMethod.POST}, params = "action=delete")
    public String cancelOrder(@RequestParam("id") Long id,
                              RedirectAttributes attributes) {

        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Order was canceled successfully!");

        return "redirect:/my-orders";
    }
    @RequestMapping(value = "/cancel-customer-order", method = {RequestMethod.POST}, params = "action=delete")
    public String cancelCustomerOrder(@RequestParam("id") Long id,
                              RedirectAttributes attributes) {

        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Order was canceled successfully!");

        return "redirect:/orders-all";
    }

    @RequestMapping(value = "/ship-order", method = {RequestMethod.POST}, params = "action=ship")
    public String acceptOrder(@RequestParam("id") Long id,
                              RedirectAttributes attributes) {

        orderService.acceptOrder(id);
        attributes.addFlashAttribute("success", "Order was shipped successfully!");

        return "redirect:/orders-all";
    }


//    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
//    public String updateCart(@RequestParam("quantity") int quantity,
//                             @RequestParam("id") Long productId,
//                             Model model,
//                             Principal principal) {
//
//        User user = userService.findByEmail(principal.getName());
//
//        Product product = productService.getProductById(productId);
//
//        ShoppingCart shoppingCart = shoppingCartService.updateItemInCart(product, quantity, user);
//
//        model.addAttribute("shoppingCart", shoppingCart);
//
//        return "redirect:/cart";
//    }
}
