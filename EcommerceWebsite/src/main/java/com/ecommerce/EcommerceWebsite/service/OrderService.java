package com.ecommerce.EcommerceWebsite.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.model.Order;
import com.ecommerce.EcommerceWebsite.model.OrderItem;
import com.ecommerce.EcommerceWebsite.repository.OrderItemRepository;
import com.ecommerce.EcommerceWebsite.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public void createOrder(String billingAddress, Long userId) {
        // Fetch cart itemns for user
        List<Cart> cartItems = cartService.getCartByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No items in cart to process");
        }

        // Create new order
        Order order = new Order();
        order.setBillingAddress(billingAddress);
        order.setCreatedAt(LocalDateTime.now());

        // Create order items
        for (Cart cart : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cart.getProduct().getId());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setUnitPrice(cart.getProduct().getPrice());
            orderItem.setOrder(order);

            order.getItems().add(orderItem);
        }
        
        // Save the order and order items
        orderRepository.save(order);
        cartService.clearCartForUser(userId); // Optionally clear the cart after order creation
    }
}