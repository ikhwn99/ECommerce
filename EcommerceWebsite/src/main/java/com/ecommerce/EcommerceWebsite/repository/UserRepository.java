package com.ecommerce.EcommerceWebsite.repository;

import com.ecommerce.EcommerceWebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByName(String name);

    void deleteByName(String name);
}

