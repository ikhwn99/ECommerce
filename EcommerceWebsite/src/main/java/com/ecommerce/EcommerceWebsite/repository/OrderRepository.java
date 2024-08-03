package com.ecommerce.EcommerceWebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.EcommerceWebsite.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
