package com.ecommerce.EcommerceWebsite.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.model.Order;
import com.ecommerce.EcommerceWebsite.model.OrderItem;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.OrderItemRepository;
import com.ecommerce.EcommerceWebsite.repository.OrderRepository;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;

@Service
public class OrderService {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createOrder(String custName, int custPhone, String custEmail, String billingAddress, Long userId, String status) {
        // Fetch cart itemns for user
        List<Cart> cartItems = cartService.getCartByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No items in cart to process");
        }

        // Create new order
        Order order = new Order();
        order.setCustName(custName);
        order.setCustPhone(custPhone);
        order.setCustEmail(custEmail);
        order.setBillingAddress(billingAddress);
        order.setStatus(status);
        order.setCreatedAt(LocalDateTime.now());

        // Fetch the user entity
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found"));

        order.setUser(user);

        double totalPrice = 0;

        // Create order items
        for (Cart cart : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cart.getProduct().getId());
            int quantity = cart.getQuantity();
            orderItem.setQuantity(quantity);
            double unitPrice = cart.getProduct().getPrice();
            orderItem.setUnitPrice(unitPrice);
            orderItem.setTotalPrice(unitPrice * quantity);
            orderItem.setOrder(order);

            totalPrice += orderItem.getTotalPrice();

            order.getItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        
        // Save the order and order items
        orderRepository.save(order);
        cartService.clearCartForUser(userId); // Optionally clear the cart after order creation
    }

    public List<Order> getOrderByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

}