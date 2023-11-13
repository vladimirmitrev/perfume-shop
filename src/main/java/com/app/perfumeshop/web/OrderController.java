package com.app.perfumeshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {


    @GetMapping("/order")
    public String orderGet() {


        return "order-checkout";
    }

    @PostMapping("/order")
    public String orderPost() {



        return "redirect:/";
    }
}
