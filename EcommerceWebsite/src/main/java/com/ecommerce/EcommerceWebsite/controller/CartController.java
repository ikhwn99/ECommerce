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

import com.ecommerce.EcommerceWebsite.config.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.*;
import com.ecommerce.EcommerceWebsite.repository.*;
import com.ecommerce.EcommerceWebsite.service.CartService;
import com.ecommerce.EcommerceWebsite.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    // @GetMapping("cart/{id}")
    // public String displayCart(@PathVariable(value = "id") Long id, Model model) {
    //     Cart cart = cartService.getCartById(id);
    //     model.addAttribute("cart", cart);
    //     return "cart";
    // }

    // @GetMapping("cart/user/{userId}")
    // public String displayCartByUser(@PathVariable(value = "userId") Long userId, Model model) {
    //     List<Cart> carts = cartService.getCartByUserId(userId);
    //     model.addAttribute("carts", carts);
    //     return "cart"; // Ensure you have a cart.html template that can handle a list of carts
    // }

    // Display cart by user id
    @GetMapping("cart")
    public String displayCartByUser(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();

        List<Cart> carts = cartService.getCartByUserId(userId);
        model.addAttribute("carts", carts);
        
        return "cart";
    }

    // Insert cart into cart table in database
    @PostMapping("/cart")
    public String saveCart(@ModelAttribute("cart") Cart cart, Principal principal, @RequestParam("user_id") Long userId, @RequestParam("product_id") Long productId, @RequestParam("price") double price, @RequestParam("quantity") int quantity, @RequestParam("product_name") String name) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found for id: " + productId));

        cart.setProductName(name);
        cart.setUser(user);
        cart.setProduct(product);
        cart.setUnitPrice(price);
        cart.setQuantity(quantity);
        cart.setPrice(price*quantity);
        
        cartService.addToCart(cart);
        return "redirect:/cart";
    }

    // Update quantity product into cart table in database
    @PostMapping("cart/updateQty") 
    public String updateQuantityInCart(@ModelAttribute("cart") Cart cart, Principal principal, @RequestParam("user_id") Long userId, @RequestParam("product_id") Long productId, @RequestParam("quantity") int quantity, @RequestParam("unit_price") double unitPrice) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("product not found for id: " + productId));

        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cart.setUnitPrice(unitPrice);

        cartService.updateQuantity(cart);
        return "redirect:/cart";
    }

    // Delete cart by cart id
    @PostMapping("/cart/delete")
    public String deleteCartItem(@RequestParam("cartItemId") Long cartItemId) {
        cartService.deleteCartById(cartItemId);
        return "redirect:/cart"; // Redirect to the cart page after deletion
    }

//    @GetMapping("/my-orders")
//    public String viewOrders() {
//        return "my_orders"; // This should match the name of your HTML file without the .html extension
//    }

    // @PostMapping("cart")
    // public String saveCart(@ModelAttribute("cart") Cart cart, @RequestParam("user_id") Long userId, @RequestParam("product_id") Long productId, @RequestParam("price") double price, @RequestParam("quantity") int quantity, @RequestParam("product_name") String name) {

    //     User user = userRepository.findById(userId)
    //         .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));
    //     Product product = productRepository.findById(productId)
    //         .orElseThrow(() -> new RuntimeException("Product not found for id: " + productId));
        
    //     cart.setProductName(name);
    //     cart.setUser(user);
    //     cart.setProduct(product);
    //     cart.setUnitPrice(price);
    //     cart.setPrice(price*quantity);

    //     cartService.updateCart(cart);
    //     return "redirect:/cart";
    // }
    

    // @PostMapping("/cart")
    // public String saveEmployee(@ModelAttribute("employee") Employee employee) {
    //     // save employee to database
    //     employeeService.saveEmployee(employee);
    //     return "redirect:/employee";
    // }
    
}
