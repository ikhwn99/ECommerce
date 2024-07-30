package com.ecommerce.EcommerceWebsite.service;

import com.ecommerce.EcommerceWebsite.model.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = null;
//        try {
//            user = userRepository.findByEmail(email);
//        } catch (UsernameNotFoundException e) {
//            System.out.println("No account with that email");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByEmail(email);
        } catch (UsernameNotFoundException e) {
            System.out.println("No account with that email");
        }
        return new CustomUserDetails(user, new ArrayList<>()); // Pass authorities as needed
    }
}