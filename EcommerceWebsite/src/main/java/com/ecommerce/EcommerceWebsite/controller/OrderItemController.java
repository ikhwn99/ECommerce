package com.ecommerce.EcommerceWebsite.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.EcommerceWebsite.model.OrderItem;
import com.ecommerce.EcommerceWebsite.service.OrderItemService;

@Controller
public class OrderItemController {

    // Autowired annotation used for dependency injection of UserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    // Autowired annotation used for dependency injection of OrderItemService
    @Autowired
    private OrderItemService orderItemService;

    // Handler method for the endpoint "/my-order-details/{orderId}
    @GetMapping("/my-order-details/{orderId}")
    public String displayMyOrderDetails(@PathVariable Long orderId, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        model.addAttribute("orderItems", orderItems);

        return "my_order_detail";
    }
}
