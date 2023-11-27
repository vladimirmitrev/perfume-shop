package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.UserRegisterDTO;
import com.app.perfumeshop.model.dto.order.OrderCheckoutDTO;
import com.app.perfumeshop.model.dto.product.AddOrUpdateProductDTO;
import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.Order;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.service.OrderService;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        if (!model.containsAttribute("shippingInfo")) {
            model.addAttribute("shippingInfo", new OrderCheckoutDTO());
        }

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

//    @ModelAttribute("shippingInfo")
//    public OrderCheckoutDTO initShippingInfo() {
//        return new OrderCheckoutDTO();
//    }

    //    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    @PostMapping("/order-checkout")
    public String addOrder(@Valid OrderCheckoutDTO shippingInfo,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model,
                           Principal principal,
                           HttpSession session) {

        model.addAttribute("shippingInfo", shippingInfo);

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("shippingInfo", shippingInfo);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.shippingInfo", bindingResult);

            return "redirect:/order-checkout";

        }

        User user = userService.findByEmail(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        Order order = orderService.placeOrder(shoppingCart, shippingInfo);

        session.removeAttribute("totalItems");
        model.addAttribute("shippingInfo", shippingInfo);
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Detail");
        model.addAttribute("page", "Order Detail");
        model.addAttribute("success", "Add order successfully");

        return "redirect:/my-orders";
    }

    @RequestMapping(value = "/cancel-order", method = {RequestMethod.POST}, params = "action=cancel")
    public String cancelOrder(@RequestParam("id") Long id,
                              RedirectAttributes attributes) {

        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Order was canceled successfully!");

        return "redirect:/my-orders";
    }

    @RequestMapping(value = "/cancel-customer-order", method = {RequestMethod.POST}, params = "action=cancel")
    public String cancelCustomerOrder(@RequestParam("id") Long id,
                                      RedirectAttributes attributes) {

        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Order was canceled successfully!");

        return "redirect:/orders-all";
    }

    @RequestMapping(value = "/ship-order", method = {RequestMethod.POST}, params = "action=ship")
    public String shipOrder(@RequestParam("id") Long id,
                            RedirectAttributes attributes) {

        orderService.shippedOrder(id);
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
