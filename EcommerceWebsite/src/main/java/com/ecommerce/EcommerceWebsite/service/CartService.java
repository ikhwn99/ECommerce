package com.ecommerce.EcommerceWebsite.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.repository.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public void updateCart(Cart cart) {
        System.out.println("Saving Cart: " + cart);
        this.cartRepository.save(cart);
    }

    // Get cart by user id
    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // Add product to cart
    public void addToCart(Cart cart) {
        Optional<Cart> existingCartOpt = cartRepository.findByUserAndProduct(cart.getUser(), cart.getProduct());
        
        if (existingCartOpt.isPresent()) {
            Cart existingCart = existingCartOpt.get();
            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
            existingCart.setPrice(existingCart.getPrice() + (cart.getUnitPrice() * cart.getQuantity()));
            cartRepository.save(existingCart);
        } else {
            cartRepository.save(cart);
        }
    }

    // Update quantity product in cart
    public void updateQuantity(Cart cart) {
        Optional<Cart> existingCartOpt = cartRepository.findByUserAndProduct(cart.getUser(), cart.getProduct());

        if (existingCartOpt.isPresent()) {
            Cart existingCart = existingCartOpt.get();
            existingCart.setQuantity(cart.getQuantity());
            existingCart.setPrice(cart.getUnitPrice() * cart.getQuantity());
            cartRepository.save(existingCart);
        } else {
            cartRepository.save(cart);
        }
    }

    // Get cart details by cart id
    public Cart getCartById(Long id) {
        Optional <Cart> optional = cartRepository.findById(id);
        Cart cart = null;
        if (optional.isPresent()) {
            cart = optional.get();
        } else {
            throw new RuntimeException(" Cart not found for id : " + id);
        }
        return cart;
    }

    // Delete cart by cart id
    public void deleteCartById(Long id) {
        this.cartRepository.deleteById(id);
    }

    public void clearCartForUser(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }

    public double calculateTotalPrice(Long userId) {
        List<Cart> carts = getCartByUserId(userId);
        
        double totalPrice = 0;

        for (Cart cart : carts) {
            totalPrice += cart.getUnitPrice() * cart.getQuantity();
        }

        return totalPrice;
    }
}
