package com.ecommerce.EcommerceWebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.EcommerceWebsite.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    List<Cart> findByUserId(Long userId);
}
