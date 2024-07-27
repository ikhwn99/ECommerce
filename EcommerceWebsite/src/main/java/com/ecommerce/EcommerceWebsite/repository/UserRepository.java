package com.ecommerce.EcommerceWebsite.repository;

import com.ecommerce.EcommerceWebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
