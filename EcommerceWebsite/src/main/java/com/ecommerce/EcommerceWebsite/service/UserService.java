package com.ecommerce.EcommerceWebsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.EcommerceWebsite.dto.UserDto;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByUsernameorEmail(String username,String email) {
        return userRepository.findByUsernameOrEmail(username,email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(UserDto userDto) {//register user from register page
        User user = new User(userDto.getEmail(), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getFullname(), userDto.getAddress(), userDto.getPhone());
        return userRepository.save(user);
    }


    public boolean save(User user) {//register user from reset password
        userRepository.save(user);
        return true;
    }
}
