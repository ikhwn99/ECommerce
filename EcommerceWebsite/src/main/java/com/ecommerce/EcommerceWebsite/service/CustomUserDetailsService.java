package com.ecommerce.EcommerceWebsite.service;

import com.ecommerce.EcommerceWebsite.model.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.PasswordResetToken;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;
import com.ecommerce.EcommerceWebsite.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TokenRepository tokenRepository;

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

    public String sendEmail(User user) {
        try {
            String resetLink = generateResetToken(user);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("ikhwnhkmi99@gmail.com");// input the senders email ID
            msg.setTo(user.getEmail());

            msg.setSubject("Product Kecantikan");
            msg.setText("Hello \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
                    + "Regards \n" + "Product Kecantikan Site");

            javaMailSender.send(msg);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

//    public String generateResetToken(User user) {
//        UUID uuid = UUID.randomUUID();
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
//        if(tokenRepository.findByUser(user)) {
//            PasswordResetToken resetToken = new PasswordResetToken();
//            resetToken.setUser(user);
//            resetToken.setToken(uuid.toString());
//            resetToken.setExpiryDateTime(expiryDateTime);
//            resetToken.setUser(user);
//            PasswordResetToken token = tokenRepository.save(resetToken);
//        }
//        if (token != null) {
//            String endpointUrl = "http://localhost:8080/resetpassword";
//            return endpointUrl + "/" + resetToken.getToken();
//        }
//        return "";
//    }

    public boolean generateOrReplaceResetToken(User user, String token) {
        PasswordResetToken existingToken = tokenRepository.findByUser(user);
        if (existingToken != null) {
            existingToken.setToken(token);
            existingToken.setExpiryDateTime(LocalDateTime.now().plusMinutes(30)); // Set your desired expiry time
            tokenRepository.save(existingToken);
        } else {
            PasswordResetToken newToken = new PasswordResetToken(token, LocalDateTime.now().plusMinutes(30) ,user);
            tokenRepository.save(newToken);
        }
        return true;
    }

    public String generateResetToken(User user) {
        String token = UUID.randomUUID().toString();
        generateOrReplaceResetToken(user, token);
        if (token != null) {
            String endpointUrl = "http://localhost:8080/resetpassword";
            return endpointUrl + "/" + token;
        }
        return "";
    }

    public boolean hasExpired(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }



}