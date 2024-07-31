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

    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

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

    public void deleteCartById(Long id) {
        this.cartRepository.deleteById(id);
    }
}
