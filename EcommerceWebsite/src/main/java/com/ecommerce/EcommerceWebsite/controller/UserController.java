package com.ecommerce.EcommerceWebsite.controller;

import com.ecommerce.EcommerceWebsite.dto.UserDto;
import com.ecommerce.EcommerceWebsite.model.User;
import com.ecommerce.EcommerceWebsite.service.UserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
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
    public String registerSava(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByUsername(userDto.getUsername());
        if (user != null) {
            model.addAttribute("Userexist", user);
            return "register";
        }
        userService.save(userDto);
        return "redirect:/login";
    }
}


// @Controller
// @RequestMapping("/users")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @PostMapping("/register")
//     public String registerUser(
//             @RequestParam("name") String name,
//             @RequestParam("email") String email,
//             @RequestParam("password") String password,
//             @RequestParam("address") String address,
//             @RequestParam("phone") int phone,
//             Model model
//     ) {
//         User newUser = new User();
//         newUser.setName(name);
//         newUser.setEmail(email);
//         newUser.setPassword(password);
//         newUser.setAddress(address);
//         newUser.setPhone(phone);

//         userService.saveUser(newUser);

//         model.addAttribute("message", "User registered successfully!");
//         return "register";
//     }

//     @GetMapping("/register")//can just use html href to go to register page
//     public String showRegistrationForm(Model model) {
//         return "register";
//     }

//     @GetMapping("/login")
//     public String loginForm(
//             @RequestParam("email") String email,
//             @RequestParam("password") String password,
//             HttpSession session,
//             Model model){

//         try{
//             if(userService.login(email,password)){
//                 session.setAttribute("userEmail", email);
//                 return "product";
//             }else{
//                 model.addAttribute("message", "Wrong details");
//                 return "login";
//             }
//         }catch (Exception e){
//             System.out.println("user not found");
//         }finally {
//             return "login";
//         }
//     }

//     @GetMapping("/logout")
//     public String logout(HttpSession session) {
//         session.invalidate();
//         return "redirect:/login";
//     }

//     @GetMapping("/deleteaccount")
//     public void deleteAccount(Long id,Model model){
//         if(userService.deleteUser(id)){
//             model.addAttribute("message", "Account Deleted Successfully");
//         }else{
//             model.addAttribute("message", "Cannot Delete Account Delete");
//         }

//     }
// }

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
