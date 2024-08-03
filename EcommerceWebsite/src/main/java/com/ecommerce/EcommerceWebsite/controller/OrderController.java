package com.ecommerce.EcommerceWebsite.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.EcommerceWebsite.config.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.Order;
import com.ecommerce.EcommerceWebsite.repository.OrderRepository;
import com.ecommerce.EcommerceWebsite.service.*;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/checkout")
    public String displayCheckoutPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        // CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // Long userId = customUserDetails.getId();

        // List<Cart> carts = cartService.getCartByUserId(userId);
        // model.addAttribute("carts", carts);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processOrder(@RequestParam("fullname") String fullname, @RequestParam("email") String email, @RequestParam("phone") int phone, @RequestParam("billingAddress") String billingAddress, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long userId = ((CustomUserDetails) userDetails).getId();

        // Create order with billing address and items from cart
        orderService.createOrder(fullname, phone, email, billingAddress, userId, "Successful");

        return "success"; // Redirect to an order confirmation page
    }

    @GetMapping("/my_orders") //GetMapping to index.html
    public String displayOrderById(Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();

        List<Order> orders = orderService.getOrderByUserId(userId);
        model.addAttribute("orders", orders);

        return "my_orders";
    }
    
}