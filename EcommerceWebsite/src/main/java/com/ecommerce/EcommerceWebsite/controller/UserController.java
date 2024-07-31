package com.ecommerce.EcommerceWebsite.controller;

import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.model.PasswordResetToken;
import com.ecommerce.EcommerceWebsite.repository.TokenRepository;
import com.ecommerce.EcommerceWebsite.service.CustomUserDetailsService;
import com.ecommerce.EcommerceWebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // The Thymeleaf template name
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("email", user.getEmail());
        return "redirect:/login";  // Redirect to login page after successful registration
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        Authentication authentication = userService.getauthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword() {
        return "forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String forgotPasswordProcess(@RequestParam String email) {
        String output = "";
        User user = userService.getUserbyEmail(email);
        if (user != null) {
            output = userDetailsService.sendEmail(user);
        }
        if (output.equals("success")) {
            return "redirect:/forgotpassword?success";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/resetpassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && userDetailsService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return "resetpassword";
        }
        return "redirect:/forgotpassword?error";
    }

    @PostMapping("/resetpassword")
    public String passwordResetProcess(@RequestParam String email,String password) {
        User user = userService.getUserbyEmail(email);
        if(user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userService.registerUser(user);
        }
        return "redirect:/login";
    }

    @GetMapping("/test")
    public String test(Model model){
        userService.getCurrentUserId();
        return "login";
    }
}


