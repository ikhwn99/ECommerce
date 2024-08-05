package com.ecommerce.EcommerceWebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.model.Product;
import com.ecommerce.EcommerceWebsite.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByUserAndProduct(User user, Product product);
    List<Cart> findByUserId(Long userId);
}
