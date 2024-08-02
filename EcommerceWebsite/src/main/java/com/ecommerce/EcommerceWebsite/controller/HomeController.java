package com.ecommerce.EcommerceWebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

//    @GetMapping("/") //GetMapping to index.html
//    public String index(){return "index";}

    @GetMapping("/success") //GetMapping to index.html
    public String success(){
        return "success";
    }

//    @GetMapping("/my_orders") //GetMapping to index.html
//    public String my_orders(){
//        return "my_orders";
//    }

    @GetMapping("/order") //GetMapping to index.html
    public String order(){
        return "order";
    }

    // @GetMapping("/login")
    // public String login(){
    //     return "login";
    // }

    // @GetMapping("/register")
    // public String register(){
    //     return "register";
    // }

    // @GetMapping("/product")
    // public String product(){
    //     return "product";
    // }

    // @GetMapping("/view_product")
    // public String view_product(){
    //     return "view_product";
    // }
}
