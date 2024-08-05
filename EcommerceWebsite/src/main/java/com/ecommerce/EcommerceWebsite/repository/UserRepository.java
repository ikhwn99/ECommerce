package com.ecommerce.EcommerceWebsite.repository;

import com.ecommerce.EcommerceWebsite.dto.UserDto;
import com.ecommerce.EcommerceWebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User save(UserDto userDto);

    User findByEmail(String email);
}
