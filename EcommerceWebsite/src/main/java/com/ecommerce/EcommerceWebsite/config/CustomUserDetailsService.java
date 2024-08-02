package com.ecommerce.EcommerceWebsite.config;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import com.ecommerce.EcommerceWebsite.model.PasswordResetToken;
import com.ecommerce.EcommerceWebsite.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{ 

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public CustomUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username or Password not found");
        }
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), authorities(), user.getFullname());
    }

    public Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
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
