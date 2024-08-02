package com.ecommerce.EcommerceWebsite.repository;

import com.ecommerce.EcommerceWebsite.model.PasswordResetToken;
import com.ecommerce.EcommerceWebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByUser(User user);

    PasswordResetToken findByToken(String token);

    void deleteById(Long id);

}
