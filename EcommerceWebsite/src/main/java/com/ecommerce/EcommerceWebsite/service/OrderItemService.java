package com.ecommerce.EcommerceWebsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.EcommerceWebsite.model.OrderItem;
import com.ecommerce.EcommerceWebsite.repository.OrderItemRepository;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    // Get a list of order item by order id
    public List<OrderItem> getOrderItemByOrderId(Long orderId) {
        return orderItemRepository.findOrderItemByOrderId(orderId);
    }
}
