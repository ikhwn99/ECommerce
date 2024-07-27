package com.ecommerce.EcommerceWebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/admin")
public class AdminController {

    
    @GetMapping("/")
    public String index(){
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(){
        return "admin/add_product";
    }

    @GetMapping("/loadCategory")
    public String loadCategory(){
        return "admin/category";
    }

    
    @GetMapping("/loadView_products")
    public String loadView_products(){
        return "admin/view_products";
    }

    
    @GetMapping("/loadOrders")
    public String loadOrders(){
        return "admin/orders";
    }
    
    @GetMapping("/loadUsers")
    public String loadUsers(){
        return "admin/users";
    }
        
    @GetMapping("/loadAdd_admin")
    public String loadAdd_admin(){
        return "admin/add_admin";
    }

}
