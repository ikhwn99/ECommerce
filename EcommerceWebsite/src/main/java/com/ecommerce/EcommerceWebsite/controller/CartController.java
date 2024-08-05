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
    private ProductRepository productRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    /**
     * Display the cart for the authenticated user.
     *
     * @param model     the model to hold attributes for the view
     * @param principal the principal representing the authenticated user
     * @return the name of the view to display the cart
     */
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
        model.addAttribute("carts_length", carts.size());
        
        
        return "cart";
    }

    /**
     * Save a cart item to the cart table in the database.
     *
     * @param cart       the cart item to be saved
     * @param principal  the principal representing the authenticated user
     * @param userId     the ID of the user
     * @param productId  the ID of the product
     * @param price      the price of the product
     * @param quantity   the quantity of the product
     * @param name       the name of the product
     * @return a redirect to the cart page
     */
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

    /**
     * Update the quantity of a product in the cart table in the database.
     *
     * @param cart       the cart item to be updated
     * @param principal  the principal representing the authenticated user
     * @param userId     the ID of the user
     * @param productId  the ID of the product
     * @param quantity   the new quantity of the product
     * @param unitPrice  the unit price of the product
     * @return a redirect to the cart page
     */
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

    /**
     * Delete a cart item by its ID.
     *
     * @param cartItemId the ID of the cart item to be deleted
     * @return a redirect to the cart page
     */
    @PostMapping("/cart/delete")
    public String deleteCartItem(@RequestParam("cartItemId") Long cartItemId) {
        cartService.deleteCartById(cartItemId);
        return "redirect:/cart"; // Redirect to the cart page after deletion
    }
    
}
