package com.ecommerce.EcommerceWebsite.controller;

import java.security.Principal;
import java.util.List;

import com.ecommerce.EcommerceWebsite.config.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.EcommerceWebsite.model.Order;
import com.ecommerce.EcommerceWebsite.model.OrderItem;
import com.ecommerce.EcommerceWebsite.service.OrderItemService;
import com.ecommerce.EcommerceWebsite.service.OrderService;

@Controller
public class OrderItemController {

    // Autowired annotation used for dependency injection of UserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    // Autowired annotation used for dependency injection of OrderItemService
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
