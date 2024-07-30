package com.ecommerce.EcommerceWebsite.controller;

import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // The Thymeleaf template name
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        userService.registerUser(user);
        return "redirect:/login";  // Redirect to login page after successful registration
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "login";
    }

    @GetMapping("/test")
    public String test(Model model){
        userService.getCurrentUserId();
        return "/login";
    }
}


//@GetMapping("/register")
//public String showRegistrationForm(Model model) {
//    model.addAttribute("user", new User());
//    return "register";
//}
//    @PostMapping("/register")
//    public String registerUser(
//            @RequestParam("name") String name,
//            @RequestParam("email") String email,
//            @RequestParam("password") String password,
//            @RequestParam("address") String address,
//            @RequestParam("phone") int phone,
//            Model model
//    ) {
//        User newUser = new User();
//        newUser.setName(name);
//        newUser.setEmail(email);
//        newUser.setPassword(password);
//        newUser.setAddress(address);
//        newUser.setPhone(phone);
//
//        userService.saveUser(newUser);
//
//        model.addAttribute("message", "User registered successfully!");
//        return "register";
//    }
//
//    @GetMapping("/register")//can just use html href to go to register page
//    public String showRegistrationForm(Model model) {
//        return "register";
//    }
//
//    @PostMapping("/login")
//    public String loginForm(
//            @RequestParam("email") String email,
//            @RequestParam("password") String password,
//            HttpSession session,
//            Model model){
//
//        try{
//            if(userService.login(email,password)){
//                session.setAttribute("userEmail", email);
//                return "product";
//            }else{
//                model.addAttribute("message", "Wrong details");
//                return "login";
//            }
//        }catch (Exception e){
//            System.out.println("user not found");
//        }finally {
//            return "login";
//        }
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/login";
//    }
//
//    @GetMapping("/deleteaccount")
//    public void deleteAccount(Long id,Model model){
//        if(userService.deleteUser(id)){
//            model.addAttribute("message", "Account Deleted Successfully");
//        }else{
//            model.addAttribute("message", "Cannot Delete Account Delete");
//        }
//
//    }
//}
//    @GetMapping
//    public String users(Model m, @RequestParam(value = "id", defaultValue = "") Long id) {
//        // Fetch the list of users based on the status (if provided)
//        List<User> users;
//        if (id == null || id == 0) {
//            users = userService.getAllUsers();
//        } else {
//            users = List.of(userService.getUserById(id));// implement method to get user by email at least
//        }
//        // Add the user list and status to the model
//        m.addAttribute("users", users);
//        m.addAttribute("paramValue", id);
//
//        // Return the view name
//        return "admin/users";
//    }
