package com.ecommerce.EcommerceWebsite.controller;

import com.ecommerce.EcommerceWebsite.config.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.config.CustomUserDetailsService;
import com.ecommerce.EcommerceWebsite.dto.UserDto;
import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.model.PasswordResetToken;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.repository.TokenRepository;

import java.security.Principal;
import java.util.List;

import com.ecommerce.EcommerceWebsite.service.CartService;
import com.ecommerce.EcommerceWebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if(principal != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("userdetail", userDetails);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new UsernameNotFoundException("User not authenticated");
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = customUserDetails.getId();

            List<Cart> carts = cartService.getCartByUserId(userId);

            model.addAttribute("carts_length", carts.size());
        }else model.addAttribute("userdetail", null);

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }
    
    @GetMapping("/register")
        public String register(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByUsernameorEmail(userDto.getUsername(),userDto.getEmail());

        if(user==null){
            userService.save(userDto);
            return "redirect:/login";
        }
        else if (user.getUsername().equals(userDto.getUsername())) {
            model.addAttribute("errorMsg","User exist");
            return "register";
        }else if(user.getEmail().equals(userDto.getEmail())){
            model.addAttribute("errorMsg","Email is used" );
            return "register";
        }
        return "register";
    }

    @GetMapping("/logout")
    public String logoutSuccess() {
        return "redirect:/login?logout";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword() {
        return "forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String forgotPasswordProcess(@RequestParam String email) {
        String output = "";
        User user = userService.getUserByEmail(email);
        if (user != null) {
            output = userDetailsService.sendEmail(user);
        }
        if (output.equals("success")) {
            return "redirect:/forgotpassword?success";
        }
        return "redirect:/forgotpassword?error";
    }

    @GetMapping("/resetpassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && userDetailsService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return "resetpassword";
        }
        return "redirect:/forgotpassword?errortoken";
    }

    @PostMapping("/resetpassword")
    public String passwordResetProcess(@RequestParam String email,String password) {
        User user = userService.getUserByEmail(email);
        if(user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
        }
        return "redirect:/login";
    }
}

