package com.ecommerce.EcommerceWebsite.service;

import com.ecommerce.EcommerceWebsite.model.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.PasswordResetToken;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.TokenRepository;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Bean
    public Authentication getauthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public boolean registerUser(User user) {
        userRepository.save(user);
        return true;
    }


    public User getUserbyEmail(String email){
        User user = new User();
        user = userRepository.findByEmail(email);
        return user;
    }

    public User getCurrentUser(){
        Authentication authentication = getauthentication();
        User currentUser = new User();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
             currentUser = userDetails.getUser();
        }

        return currentUser;
    }

    public String getCurrentUserId() {
        Authentication authentication = getauthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUser().getName());
            return userDetails.getUser().getId().toString(); // Return user ID
        } else {
            throw new RuntimeException("User details not found or not of type CustomUserDetails");
        }
    }

    public boolean removeUserbyEmail(String email){
        return userRepository.deleteByEmail(email);
    }

    public boolean passwordResetbyEmail(Long id,String email){
        tokenRepository.deleteById(id);
        return userRepository.deleteByEmail(email);
    }
}
