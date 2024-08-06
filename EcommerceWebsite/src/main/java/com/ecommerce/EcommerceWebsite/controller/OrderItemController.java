package com.ecommerce.EcommerceWebsite.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ecommerce.EcommerceWebsite.config.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.EcommerceWebsite.model.*;
import com.ecommerce.EcommerceWebsite.service.*;

@Controller
public class OrderItemController {

    // Autowired annotation used for dependency injection of UserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    // Handler method for the endpoint "/my-order-details/{orderId}
    @GetMapping("/my-order-details/{orderId}")
    public String displayMyOrderDetails(@PathVariable Long orderId, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        model.addAttribute("orderItems", orderItems);

        Order order = orderService.getOrderById(orderId);
        // Changes the date into format dd MMMM yyyy)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        order.setFormattedDate(order.getCreatedAt().format(formatter));
        model.addAttribute("order", order);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();

        List<Cart> carts = cartService.getCartByUserId(userId);

        model.addAttribute("carts_length", carts.size());

        return "my_order_detail";
    }
}
