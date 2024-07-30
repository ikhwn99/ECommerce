package com.ecommerce.EcommerceWebsite.service;

import com.ecommerce.EcommerceWebsite.model.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUser().getName());
            return userDetails.getUser().getId().toString(); // Return user ID
        } else {
            throw new RuntimeException("User details not found or not of type CustomUserDetails");
        }
    }
}
