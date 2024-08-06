package com.ecommerce.EcommerceWebsite.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.model.Order;
import com.ecommerce.EcommerceWebsite.model.OrderItem;
import com.ecommerce.EcommerceWebsite.repository.OrderRepository;
import com.ecommerce.EcommerceWebsite.service.*;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemService orderItemService;

    // Handling requests to the '/checkout' endpoint
    @GetMapping("/checkout")
    public String displayCheckoutPage(Model model, Principal principal) {
        // Retrieve the details of the currently authenticated user
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        // Check if the user is authenticated. If the user is not authenticated, it will throws an exception to ensure that only authenticated users can access the checkout page
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        // Get the userId by 'CustomeUserDetails' from the 'Authentication' object
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();

        // Retrieves the list of cart items based on user id. Added the list to the model with the attributes name 'carts'
        List<Cart> carts = cartService.getCartByUserId(userId);
        model.addAttribute("carts", carts);

        // Calculates the total price of the items in the cart by calling the method calculateTotalPrice(Long userId) from Cart Service. Added the totalPrice data to the model attribute name 'totalPrice'
        double totalPrice = cartService.calculateTotalPrice(userId);
        model.addAttribute("totalPrice", totalPrice);

        // Get the total number of item in cart by using method size() for carts object. It will displayed the number of item in cart at the cart icon in navigation bar
        model.addAttribute("carts_length", carts.size());
        return "checkout";
    }

    // Handle the submission of the checkout form from checkout.html
    // processOrder method retrieve the fullname, email, phone, billingAddress parameter from the request using @RequestParam and binds it to the corresponding variable. principal parameter provides the access to the currently authenticated user's detail
    @PostMapping("/checkout")
    public String processOrder(@RequestParam("fullname") String fullname, @RequestParam("email") String email, @RequestParam("phone") int phone, @RequestParam("billingAddress") String billingAddress, Principal principal) {

        // Get the userId by 'CustomeUserDetails' from the 'Authentication' object
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long userId = ((CustomUserDetails) userDetails).getId();

        // Create order with customer detail including fullname, phone, email and billing address
        orderService.createOrder(fullname, phone, email, billingAddress, userId, "Successful");

        return "success"; 
    }

    // Handling requests to the '/my-orders' endpoint
    @GetMapping("/my-orders")
    public String displayOrderById(Model model, Principal principal){

        // Get the userId by 'CustomeUserDetails' from the 'Authentication' object
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        // Check if the user is authenticated. If the user is not authenticated, it will throws an exception to ensure that only authenticated users can access the checkout page
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        // Get the userId by 'CustomeUserDetails' from the 'Authentication' object
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();

        // Get the list of order using getOrderByUserId()
        List<Order> orders = orderService.getOrderByUserId(userId);

        // Changes the date into format dd MMMM yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        for (Order order : orders) {
            order.setFormattedDate(order.getCreatedAt().format(formatter));
        }
        model.addAttribute("orders", orders);

        // Create a map to store order items by order ID
        // For each order, the method retrieves the list of order items and populates the map with the order ID as the key and the list of order items as the value.
        Map<Long, List<OrderItem>> orderItemsMap = new HashMap<>();
        for (Order order : orders) {
            Long orderId = order.getId();
            List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
            orderItemsMap.put(orderId, orderItems);
        }
        model.addAttribute("orderItemsMap", orderItemsMap);

        // Get the cart detail based on user id
        List<Cart> carts = cartService.getCartByUserId(userId);
        model.addAttribute("carts_length", carts.size());

        return "my_orders";
    }
    
}