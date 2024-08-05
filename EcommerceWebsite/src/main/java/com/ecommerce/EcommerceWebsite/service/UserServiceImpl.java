package com.ecommerce.EcommerceWebsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.EcommerceWebsite.dto.UserDto;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserDto userDto) {//register user from register page
        User user = new User(userDto.getEmail(), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getFullname(), userDto.getAddress(), userDto.getPhone());
        return userRepository.save(user);
    }


    public boolean save(User user) {//register user from reset password
        userRepository.save(user);
        return true;
    }
}
