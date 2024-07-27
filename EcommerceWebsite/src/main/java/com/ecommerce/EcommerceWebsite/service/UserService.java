package com.ecommerce.EcommerceWebsite.service;

import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    public User saveUser(User user) {
        String hashedpassword = hashPassword(user.getPassword());
        user.setPassword(hashedpassword);
        return userRepository.save(user);
    }
//    public User updateUser(Long id,User user) {
//        return userRepository.save(user);
//    }

    public boolean login(String email,String password){
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return checkPassword(password, user.getPassword());
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
